package pl.olafcio.bedrite.util;

import net.minecraft.client.gui.widget.ButtonWidget;
import pl.olafcio.bedrite.mixininterface.IButtonWidget;

public abstract class ImAButton
       extends ButtonWidget
       implements IButtonWidget
{
    public ImAButton(int i, int j, int k, String string) {
        super(i, j, k, string);
    }

    public ImAButton(int i, int j, int k, int l, int m, String string) {
        super(i, j, k, l, m, string);
    }
}
