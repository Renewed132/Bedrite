package pl.olafcio.bedrite.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.olafcio.bedrite.mixininterface.ITextRenderer;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    //---------//
    // BUTTONS //
    //---------//

//    @ModifyConstant(constant = {
//            @Constant(intValue = 100)
//    }, method = {"init", "method_1724"})
//    public int buttonWidth__screenHalf__minus(int constant) {
//        return 98;
//    }
//
//    @Redirect(at = @At(value = "NEW", target = "(IIILjava/lang/String;)Lnet/minecraft/client/gui/widget/ButtonWidget;"), method = {"init", "method_1724"})
//    public ButtonWidget button__init(int x, int y, int color, String text) {
//        return new ButtonWidget(x, y, color, 98, 20, text);
//    }

    //-------//
    // TEXTS //
    //-------//

    @ModifyConstant(constant = {
            @Constant(stringValue = "Minecraft 1.3.2")
    }, method = "render")
    public String render__minecraftText(String constant) {
        return "v1.3.2";
    }

    @ModifyConstant(constant = {
            @Constant(stringValue = "Copyright Mojang AB. Do not distribute!")
    }, method = "render")
    public String render__copyright(String constant) {
        return "©Mojang AB";
    }

    //------//
    // TEXT //
    //------//

    @Unique
    private int textOffset;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), method = "render")
    public void render__drawText(TitleScreen instance, TextRenderer textRenderer, String text, int x, int y, int color) {
        int x1 = x;
        int x2 = x + textRenderer.getStringWidth(text);

        if (textOffset > 0) x2 += textOffset;
        else                x1 += textOffset;

        fill(x1, y-3, x2, y+textRenderer.fontHeight+1, 0x77000000);
        ((ITextRenderer) textRenderer).drawString(text, x + textOffset, y, color, false);
    }

    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0), method = "render")
    public void render__drawText__0(Args args) {
        textOffset = -1;
        args.set(2, this.width - this.textRenderer.getStringWidth(args.get(1)));
    }

    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 1), method = "render")
    public void render__drawText__1(Args args) {
        textOffset = 1;
        args.set(2, 0);
    }
}
