package pl.olafcio.bedrite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getWidth()I"), method = "render")
    public int getWidth(Window instance) {
        return instance.getWidth() - 20;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getHeight()I"), method = "render")
    public int getHeight(Window instance) {
        return instance.getHeight() - 20;
    }

    @WrapMethod(method = {
            "renderNausea",
            "renderVignette"
    })
    public void renderNV(float x, int width, int height, Operation<Void> original) {
        original.call(x, width + 20, height + 20);
    }

    @WrapMethod(method = "renderPumpkinBlur")
    public void renderPumpkinBlur(int width, int height, Operation<Void> original) {
        original.call(width + 20, height + 20);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;isSpectator()Z"), method = "render")
    public void isSpectator(float partialTick, boolean inScreen, int mouseX, int mouseY, CallbackInfo ci) {
        GL11.glTranslatef(10F, 10F, 0F);
    }

    @Inject(at = @At("TAIL"), method = "render")
    public void tail(float partialTick, boolean inScreen, int mouseX, int mouseY, CallbackInfo ci) {
        GL11.glTranslatef(-10F, -10F, 0F);
    }

    // CUSTOM ELEMENTS //
    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;debugEnabled:Z", opcode = Opcodes.GETFIELD), method = "render")
    public boolean debugEnabled(GameOptions instance) {
        boolean value = instance.debugEnabled;
        if (!value) {
            Minecraft mc = Minecraft.getMinecraft();
            String text = String.format("Position: %d, %d, %d", (int)mc.playerEntity.x, (int)mc.playerEntity.y, (int)mc.playerEntity.z);
            int y = 55;

            DrawableHelper.fill(-20, y, -20 + 9*2 + mc.textRenderer.getStringWidth(text), y + 16, 0xaa000000);
            mc.textRenderer.method_956(
                    text,
                    -6, y + 4,
                    0xffffffff
            );
        }

        return value;
    }
}
