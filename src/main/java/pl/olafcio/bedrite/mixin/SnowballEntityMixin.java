package pl.olafcio.bedrite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.thrown.SnowballEntity;
import net.minecraft.entity.thrown.ThrowableEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.bedrite.Feature;

@Mixin(SnowballEntity.class)
@Feature("Snowballs catching on fire and putting this state on mob hit")
public abstract class SnowballEntityMixin extends ThrowableEntity {
    public SnowballEntityMixin(World world) {
        super(world);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;I)Z"), method = "onCollision")
    public boolean damage(Entity instance, DamageSource source, int damage, Operation<Boolean> original) {
        if (isOnFire())
            instance.setOnFireFor(8);

        return original.call(instance, source, damage);
    }

    @Override
    protected void setOnFireFromLava() {
        if (!this.isFireImmune)
            this.damage(DamageSource.LAVA, 4);

        this.setOnFireFor(15);
    }

    @Override
    public boolean method_2469() {
        boolean res = super.method_2469();
        if (this.world.containsFireSource(this.boundingBox.increment(0.001, 0.001, 0.001)))
            this.setOnFireFor(8);

        return res;
    }
}
