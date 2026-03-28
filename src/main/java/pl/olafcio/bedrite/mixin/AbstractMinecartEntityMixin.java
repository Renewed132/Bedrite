package pl.olafcio.bedrite.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.bedrite.Feature;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin extends Entity {
    @Shadow public int field_3897;

    public AbstractMinecartEntityMixin(World world) {
        super(world);
    }

    @Feature("Make golems be able to go in minecarts")
    @Inject(at = @At("HEAD"), method = "pushAwayFrom", cancellable = true)
    public void pushAwayFrom(Entity entity, CallbackInfo ci) {
        ci.cancel();

        if (!this.world.isClient) {
            if (entity != this.rider) {
                if (entity instanceof MobEntity
                        && !(entity instanceof PlayerEntity)
                        && this.field_3897 == 0
                        && this.velocityX * this.velocityX + this.velocityZ * this.velocityZ > 0.01
                        && this.rider == null
                        && entity.vehicle == null) {
                    entity.startRiding(this);
                }

                double var2 = entity.x - this.x;
                double var4 = entity.z - this.z;
                double var6 = var2 * var2 + var4 * var4;
                if (var6 >= 1.0E-4F) {
                    var6 = MathHelper.sqrt(var6);
                    var2 /= var6;
                    var4 /= var6;
                    double var8 = 1.0 / var6;
                    if (var8 > 1.0) {
                        var8 = 1.0;
                    }

                    var2 *= var8;
                    var4 *= var8;
                    var2 *= 0.1F;
                    var4 *= 0.1F;
                    var2 *= 1.0F - this.pushSpeedReduction;
                    var4 *= 1.0F - this.pushSpeedReduction;
                    var2 *= 0.5;
                    var4 *= 0.5;
                    if (entity instanceof AbstractMinecartEntity) {
                        double var10 = entity.x - this.x;
                        double var12 = entity.z - this.z;
                        Vec3d var14 = Vec3d.getVec3dPool().getOrCreate(var10, 0.0, var12).normalize();
                        Vec3d var15 = Vec3d.getVec3dPool()
                                .getOrCreate(MathHelper.cos(this.yaw * (float) Math.PI / 180.0F), 0.0, MathHelper.sin(this.yaw * (float) Math.PI / 180.0F))
                                .normalize();
                        double var16 = Math.abs(var14.dotProduct(var15));
                        if (var16 < 0.8F) {
                            return;
                        }

                        double var18 = entity.velocityX + this.velocityX;
                        double var20 = entity.velocityZ + this.velocityZ;
                        if (((AbstractMinecartEntity)entity).field_3897 == 2 && this.field_3897 != 2) {
                            this.velocityX *= 0.2F;
                            this.velocityZ *= 0.2F;
                            this.addVelocity(entity.velocityX - var2, 0.0, entity.velocityZ - var4);
                            entity.velocityX *= 0.95F;
                            entity.velocityZ *= 0.95F;
                        } else if (((AbstractMinecartEntity)entity).field_3897 != 2 && this.field_3897 == 2) {
                            entity.velocityX *= 0.2F;
                            entity.velocityZ *= 0.2F;
                            entity.addVelocity(this.velocityX + var2, 0.0, this.velocityZ + var4);
                            this.velocityX *= 0.95F;
                            this.velocityZ *= 0.95F;
                        } else {
                            var18 /= 2.0;
                            var20 /= 2.0;
                            this.velocityX *= 0.2F;
                            this.velocityZ *= 0.2F;
                            this.addVelocity(var18 - var2, 0.0, var20 - var4);
                            entity.velocityX *= 0.2F;
                            entity.velocityZ *= 0.2F;
                            entity.addVelocity(var18 + var2, 0.0, var20 + var4);
                        }
                    } else {
                        this.addVelocity(-var2, 0.0, -var4);
                        entity.addVelocity(var2 / 4.0, 0.0, var4 / 4.0);
                    }
                }
            }
        }
    }
}
