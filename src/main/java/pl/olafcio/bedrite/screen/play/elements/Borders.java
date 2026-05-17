package pl.olafcio.bedrite.screen.play.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

public final class Borders extends ButtonWidget {
    public Borders(int i, int j, int k, int l, int m, String string) {
        super(i, j, k, l, m, string);
    }

    @Override
    public void method_891(Minecraft minecraft, int i, int j) {
        // Horizontal Left
        fill(x, y, x+1, y+height-3, 0xFF000000);
        fill(x+1, y, x+1+3, y+height-3, 0xFFFFFFFF);
        fill(x+3, y, x+3+4, y+height-3, 0xFFC6C6C6);

        // Vertical Top
        fill(x, y, x+width-3, y+1, 0xFF000000);
        fill(x+width-3, y+0, x+width-2, y+1, 0xFF000000);
        fill(x+width-2, y+1, x+width-1, y+2, 0xFF000000);
        fill(x+width-1, y+2, x+width, y+3, 0xFF000000);

        fill(x+1, y+1, x+width-2, y+2, 0xFFFFFFFF);
        fill(x+1, y+2, x+width-1, y+3, 0xFFFFFFFF);

        fill(x+3+4, y+3, x+width-3, y+3+4, 0xFFC6C6C6);
        fill(x+width-2, y+2, x+width-1, y+3, 0xFF999999);

        // Horizontal Right
        fill(x+width, y+3, x+width-1, y+height-2, 0xFF000000);
        fill(x+width-1, y+3, x+width-1-3, y+height-2, 0xFF585858);
        fill(x+width-3, y+3, x+width-3-4, y+height-2, 0xFFC6C6C6);

        // Vertical Bottom
        ///vb; black dots left
        fill(x+1, y+height-3, x+2, y+height-2, 0xFF000000);
        fill(x+2, y+height-2, x+3, y+height-1, 0xFF000000);
        fill(x+3, y+height-1, x+4, y+height-0, 0xFF000000);

        ///vb; gray line x1-x2
        fill(x+2, y+height-3, x+width-1, y+height-2, 0xFF555555);
        fill(x+3, y+height-2, x+width-2, y+height-1, 0xFF555555);

        ///vb; black line x1-x2
        fill(x+3, y+height-1, x+width-2, y+height, 0xFF000000);

        ///vb; black single dot right
        fill(x+width-2, y+height-2, x+width-1, y+height-1, 0xFF000000);

        ///vb; gray single dot right
        fill(x+width-4, y+height-4, x+width-3, y+height-3, 0xFF555555);

        ///vb: light-gray bold line x1-x2
        fill(x+3, y+height-3-4, x+width-4, y+height-3, 0xFFC6C6C6);
    }
}
