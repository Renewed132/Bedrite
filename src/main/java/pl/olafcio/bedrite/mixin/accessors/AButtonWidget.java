package pl.olafcio.bedrite.mixin.accessors;

import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ButtonWidget.class)
public interface AButtonWidget {
    @Accessor("width")
    int bedrite$width();

    @Accessor("height")
    int bedrite$height();
}
