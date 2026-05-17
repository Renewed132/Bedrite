package pl.olafcio.bedrite.screen.play.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

public final class Background extends ButtonWidget {
    public Background(int i, int j, int k, int l, int m, String string) {
        super(i, j, k, l, m, string);
    }

    @Override
    public void method_891(Minecraft minecraft, int i, int j) {
        fill(x, y, x+width, y+height, 0xCC000000);
    }
}
