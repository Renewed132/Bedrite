package pl.olafcio.bedrite.mixin;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pl.olafcio.bedrite.Feature;

@Mixin(ExperienceOrbEntity.class)
@Feature("Remove experience pickup delay")
public class ExperienceOrbEntityMixin {
    @Redirect(
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;experiencePickUpDelay:I",
                    opcode = Opcodes.GETFIELD
            ),
            method = "onPlayerCollision"
    )
    public int onPlayerCollision__pickupDelay__get(PlayerEntity instance) {
        return instance.experiencePickUpDelay;
    }

    @Redirect(
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;experiencePickUpDelay:I",
                    opcode = Opcodes.PUTFIELD
            ),
            method = "onPlayerCollision"
    )
    public void onPlayerCollision__pickupDelay__set(PlayerEntity instance, int value) {}
}
