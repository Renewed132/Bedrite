package pl.olafcio.bedrite.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import pl.olafcio.bedrite.mixininterface.ITextRenderer;

@Mixin(ButtonWidget.class)
public class ButtonWidgetMixin {
    @ModifyConstant(constant = {
            @Constant(intValue = 0xE0E0E0)
    }, method = "method_891")
    public int render__textColor(int constant) {
        return 0x4b4b4b;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;drawCenteredString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), method = "method_891")
    public void render__drawCenteredString(ButtonWidget instance, @Coerce ITextRenderer textRenderer, String text, int centerX, int y, int color) {
        textRenderer.drawCenteredStringWithoutShadow(text, centerX, y, color, false);
    }
}
