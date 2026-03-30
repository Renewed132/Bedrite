package pl.olafcio.bedrite.mixin;

import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import pl.olafcio.bedrite.mixininterface.ITextRenderer;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin implements ITextRenderer {
    @Shadow protected abstract int method_957(String string, int i, int j, int k, boolean bl);

    @Override
    public int drawString(String string, int x, int y, int color, boolean darken) {
        return method_957(string, x, y, color, darken);
    }
}
