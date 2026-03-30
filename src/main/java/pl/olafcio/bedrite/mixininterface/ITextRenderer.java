package pl.olafcio.bedrite.mixininterface;

import net.minecraft.client.font.TextRenderer;

public interface ITextRenderer {
    int drawString(String string, int x, int y, int color, boolean darken);

    default void drawCenteredStringWithoutShadow(String text, int centerX, int y, int color, boolean darken) {
        this.drawString(text, centerX - ((TextRenderer) this).getStringWidth(text) / 2, y, color, darken);
    }
}
