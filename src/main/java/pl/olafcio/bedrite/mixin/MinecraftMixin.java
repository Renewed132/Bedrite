package pl.olafcio.bedrite.mixin;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.PixelFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/PixelFormat;withDepthBits(I)Lorg/lwjgl/opengl/PixelFormat;"), method = "initializeGame")
    public PixelFormat initializeGame__createDisplay__pixelFormat(PixelFormat pixelFormat, int depth) {
        return pixelFormat.withDepthBits(4)
                          .withSamples(4);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;create(Lorg/lwjgl/opengl/PixelFormat;)V", shift = At.Shift.AFTER), method = "initializeGame")
    public void initializeGame__createDisplay(CallbackInfo ci) {
        glEnable(GL_MULTISAMPLE);
    }
}
