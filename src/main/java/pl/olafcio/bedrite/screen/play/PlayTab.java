package pl.olafcio.bedrite.screen.play;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;
import java.util.function.Consumer;

public abstract class PlayTab {
    public List<ButtonWidget> buttons;
    public Minecraft mc;
    public PlayScreen screen;

    public int insideX, insideY,
               insideWidth, insideHeight;

    public int paddingX, paddingY;
    public int scrollY;

    protected abstract void init();

    protected final void startElementRendering(Minecraft mc, Runnable callback) {
        screen.startElementRendering(mc, callback);
    }

    protected final void startElementRendering(Minecraft mc, int mouseY, Consumer<Integer> callback) {
        screen.startElementRendering(mc, mouseY, callback);
    }

    protected final int getMaxScrollY() {
        return screen.getMaxScrollY();
    }
}
