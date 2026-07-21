package pl.olafcio.bedrite.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pl.olafcio.bedrite.Feature;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends MobEntity {
    @Shadow
    public PlayerAbilities abilities;

    @Shadow
    public PlayerInventory inventory;

    public PlayerEntityMixin(World world) {
        super(world);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isOutOfWorld()Z"), method = "damage")
    @Feature("Complete creative-mode invulnerability, even from void")
    public boolean damage__isOutOfWorld__whenInvulnerable(DamageSource instance) {
        return abilities.creativeMode
                    ? false
                    : instance.isOutOfWorld();
    }

    /**
     * @author Bedrite
     * @reason Bedrock Edition differences. I do not want to spend a whole hour doing magic annotations for ts
     */
    @Overwrite
    public float getMiningSpeed(Block block) {
        float dmg = this.inventory.getMiningSpeed(block);

        int efficiency = EnchantmentHelper.method_3530(this.inventory);
        if (efficiency > 0 && this.inventory.canToolBreak(block))
            dmg += (float)(efficiency * efficiency + 1);

        if (this.hasStatusEffect(StatusEffect.HASTE))
            dmg *= 1.0f + (float)(this.getStatusEffect(StatusEffect.HASTE).getAmplifier() + 1) * 0.2f;

        if (this.hasStatusEffect(StatusEffect.MINING_FATIGUE))
            dmg *= (float) Math.pow(0.33f, (float)(this.getStatusEffect(StatusEffect.MINING_FATIGUE).getAmplifier() + 1));

        if (this.isSubmergedIn(Material.WATER) && !EnchantmentHelper.method_3537(this.inventory))
            dmg /= 5.0f;

        if (!this.onGround)
            dmg /= 5.0f;

        if (this.hasStatusEffect(StatusEffect.HASTE))
            dmg *= (float) Math.pow(1.2f, (float)(this.getStatusEffect(StatusEffect.HASTE).getAmplifier() + 1));

        if (this.hasStatusEffect(StatusEffect.MINING_FATIGUE))
            dmg *= (float) Math.pow(0.7f, (float)(this.getStatusEffect(StatusEffect.MINING_FATIGUE).getAmplifier() + 1));

        return dmg;
    }
}
