package pl.olafcio.bedrite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.widget.LanguageButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LanguageButton.class)
public class LanguageButtonMixin {
    @ModifyConstant(constant = {
            @Constant(intValue = 20)
    }, method = "<init>")
    private static int init__20(int constant) {
        return 23;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/LanguageButton;drawTexture(IIIIII)V"), method = "method_891")
    public void render__drawTexture(LanguageButton instance, int x, int y, int u, int v, int w, int h, Operation<Void> original) {
        original.call(
                instance,
                x, y,
                20 + (v-106), 106,
                23, 23
        );
    }
}
