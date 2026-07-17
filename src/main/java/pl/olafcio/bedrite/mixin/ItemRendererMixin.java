package pl.olafcio.bedrite.mixin;

import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import pl.olafcio.bedrite.mixininterface.IItemRenderer;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin implements IItemRenderer {
    @ModifyConstant(constant = {
            @Constant(intValue = 0xFFFFFF)
    }, method = "renderItem")
    public int renderItem__color(int constant) {
        return yellowAmount ? 0xFFFF00 : constant;
    }

    @Unique
    private boolean yellowAmount = false;

    @Override
    public void bedrite$yellowAmount(boolean value) {
        yellowAmount = value;
    }
}
