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

import java.util.List;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    //---------//
    // BUTTONS //
    //---------//

    @ModifyConstant(constant = {
            @Constant(intValue = 100)
    }, method = {"init", "method_1724"})
    public int buttonWidth__screenHalf__minus(int constant) {
        return 146/2;
    }

    @ModifyConstant(constant = {
            @Constant(intValue = 24, ordinal = 1),
            @Constant(intValue = 24, ordinal = 2)
    }, method = "init")
    public int button__heightNpad(int constant) {
        return 30 + 2;
    }

    @Redirect(at = @At(value = "NEW", target = "(IIILjava/lang/String;)Lnet/minecraft/client/gui/widget/ButtonWidget;"), method = {"init", "method_1724"})
    public ButtonWidget button__init(int id, int x, int y, String text) {
        return new ButtonWidget(id, x, y, 146, 30, text);
    }

    @ModifyConstant(constant = {
            @Constant(intValue = 48, ordinal = 0)
    }, method = "init")
    public int button__start(int constant) {
        return 56;
    }

    @ModifyConstant(constant = {
            @Constant(intValue = 48, ordinal = 1)
    }, method = "init")
    public int button__heightNpad2(int constant) {
        return (30+2) * 2;
    }

    //--------//
    // BUTTON //
    //--------//

    @Redirect(at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 3), method = "init")
    public <E> boolean init__addButton__quit(List<E> instance, E e) {
        return false;
    }

    @ModifyConstant(constant = {
            @Constant(intValue = 98, ordinal = 0)
    }, method = "init") public int init__newButton__options__width(int constant) {
        return 146;
    }
    @ModifyConstant(constant = {
            @Constant(intValue = 20, ordinal = 0)
    }, method = "init") public int init__newButton__options__height(int constant) {
        return 30;
    }

    @ModifyConstant(constant = {
            @Constant(intValue = 72)
    }, method = "init")
    public int init__newButton__optLangQuit__y(int constant) {
        return (30+2)*3 - 12;
    }

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
