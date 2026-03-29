package pl.olafcio.bedrite.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pl.olafcio.bedrite.Feature;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;collides()Z"), method = "updateTargetedEntity")
    @Feature("Click-through entities passing out with the death animation")
    public boolean updateTargetedEntity__collides(Entity entity) {
        return entity.collides() && (
                !(entity instanceof MobEntity) ||
                ((MobEntity) entity).deathTime == 0
        );
    }
}
