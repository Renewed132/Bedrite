package pl.olafcio.bedrite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerAbilities;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.bedrite.Feature;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Shadow protected Minecraft field_1759;

    @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;flying:Z", opcode = Opcodes.PUTFIELD), method = "method_2651")
    @Feature("Stop momentum when stopped flying")
    public void toggleFlying(PlayerAbilities instance, boolean value, Operation<Void> original) {
        original.call(instance, value);

        if (!value)
            field_1759.playerEntity.setVelocityClient(0, 0, 0);
    }
}
