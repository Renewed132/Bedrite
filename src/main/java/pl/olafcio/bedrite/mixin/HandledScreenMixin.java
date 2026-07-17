package pl.olafcio.bedrite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.TextureManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.bedrite.Feature;
import pl.olafcio.bedrite.mixininterface.IItemRenderer;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {
    @WrapOperation(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/client/TextureManager;Lnet/minecraft/item/ItemStack;II)V"
            ),
            method = "render"
    )
    @Feature("Make cursor items amount text yellow")
    public void render__renderHotbarItem(ItemRenderer instance, TextRenderer textRenderer, TextureManager textureManager, ItemStack item, int x, int y, Operation<Void> original) {
        ((IItemRenderer) instance).bedrite$yellowAmount(true);
        original.call(instance, textRenderer, textureManager, item, x, y);
        ((IItemRenderer) instance).bedrite$yellowAmount(false);
    }
}
