package pl.olafcio.bedrite.screen.play.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

public final class Frame extends ButtonWidget {
    public Frame(int i, int j, int k, int l, int m, String string) {
        super(i, j, k, l, m, string);
    }

    @Override
    public void method_891(Minecraft minecraft, int i, int j) {
        // Top & Left
        fill(x, y, x+1, y+height-1, 0xFF3b3b3b);
        fill(x, y, x+width-1, y+1, 0xFF3b3b3b);

        fill(x+width-1, y, x+width, y+1, 0xFF969696);
        fill(x, y+height-1, x+1, y+height, 0xFF969696);

        // Bottom & Right
        fill(x+1, y+height-1, x+width, y+height, 0xFFFFFFFF);
        fill(x+width-1, y+1, x+width, y+height, 0xFFFFFFFF);
    }
}
