package pl.olafcio.bedrite.mixininterface;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import pl.olafcio.bedrite.mixin.accessors.AButtonWidget;

public interface IButtonWidget {
    default boolean isHovered(Minecraft minecraft, int i, int j) {
        ButtonWidget btn = (ButtonWidget) this;
        AButtonWidget accessor = (AButtonWidget) btn;

        return i >= btn.x                            && j >= btn.y                             &&
               i <  btn.x + accessor.bedrite$width() && j <  btn.y + accessor.bedrite$height();
    }
}
