package pl.olafcio.bedrite.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.olafcio.bedrite.mixininterface.IButtonWidget;
import pl.olafcio.bedrite.mixininterface.ITextRenderer;

@Mixin(ButtonWidget.class)
public abstract class ButtonWidgetMixin
       implements IButtonWidget
{
    @Shadow
    public abstract boolean method_894(Minecraft minecraft, int i, int j);

    @Shadow
    public int x;

    @Shadow
    public int y;

    @Shadow
    protected int width;

    @Shadow
    protected int height;

    @ModifyConstant(constant = {
            @Constant(intValue = 0xE0E0E0)
    }, method = "method_891")
    public int render__textColor(int constant) {
        return 0x4b4b4b;
    }

    @ModifyConstant(constant = {
            @Constant(intValue = 0xffffa0)
    }, method = "method_891")
    public int render__textColor__hover(int constant) {
        return hovered ? 0xefefef : 0x4b4b4b;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;drawCenteredString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), method = "method_891")
    public void render__drawCenteredString(ButtonWidget instance, @Coerce ITextRenderer textRenderer, String text, int centerX, int y, int color) {
        textRenderer.drawCenteredStringWithoutShadow(text, centerX, y, color, false);
    }

    @ModifyConstant(constant = {
            @Constant(intValue = 46)
    }, method = "method_891")
    public int texture_v(int constant) {
        return 146;
    }

    @ModifyConstant(constant = {
            @Constant(intValue = 20)
    }, method = "method_891")
    public int texture_v__state_mul(int constant) {
        return 30;
    }

    @Unique
    private boolean hovered = false;

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;x:I", ordinal = 0, opcode = Opcodes.GETFIELD), method = "method_891")
    public void setupHovered(Minecraft minecraft, int mouseX, int mouseY, CallbackInfo ci) {
        hovered = isHovered(minecraft, mouseX, mouseY);
    }

    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;getYImage(Z)I"), method = "method_891")
    public void getYImage(Args args) {
        args.set(0, hovered);
    }
}
