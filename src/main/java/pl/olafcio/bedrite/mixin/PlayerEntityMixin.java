package pl.olafcio.bedrite.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pl.olafcio.bedrite.Feature;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Shadow
    public PlayerAbilities abilities;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isOutOfWorld()Z"), method = "damage")
    @Feature("Complete creative-mode invulnerability, even from void")
    public boolean damage__isOutOfWorld__whenInvulnerable(DamageSource instance) {
        return abilities.creativeMode
                    ? false
                    : instance.isOutOfWorld();
    }
}
