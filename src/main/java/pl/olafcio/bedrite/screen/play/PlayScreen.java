package pl.olafcio.bedrite.screen.play;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import pl.olafcio.bedrite.mixin.accessors.AButtonWidget;
import pl.olafcio.bedrite.mixin.accessors.ATitleScreen;
import pl.olafcio.bedrite.screen.play.elements.Background;
import pl.olafcio.bedrite.screen.play.elements.Borders;
import pl.olafcio.bedrite.screen.play.elements.Frame;
import pl.olafcio.bedrite.screen.play.tab.Worlds;
import pl.olafcio.bedrite.util.ImAButton;
import pl.olafcio.renewed.mixininterface.IMinecraft;

import java.util.function.Consumer;

public class PlayScreen extends Screen {
    private final TitleScreen titlescreen;
    public PlayScreen(TitleScreen titlescreen) {
        this.titlescreen = titlescreen;
    }

    private PlayTab tab;

    @Override
    public void init() {
        int boxWidth = scaleX(564);
        int boxHeight = scaleY(420);

        int offset = scaleY(53);

        buttons.add(new Borders(-1, (this.width - boxWidth)/2, (this.height - boxHeight)/2 + offset, boxWidth, boxHeight-offset, "Box"));
        buttons.add(new Frame(-1, (this.width - boxWidth) / 2 + 3 + 4, (this.height - boxHeight)/2 + offset + 3 + 4, boxWidth - (3 + 4)*2, boxHeight-offset - (3 + 4)*2, "Frame"));
        buttons.add(new Background(-1, (this.width - boxWidth) / 2 + 3 + 4 + 1, (this.height - boxHeight)/2 + offset + 3 + 4 + 1, boxWidth - (3 + 4 + 1)*2, boxHeight-offset - (3 + 4 + 1)*2, "Frame"));

//        buttons.add();

        int insideWidth  = boxWidth           - (3 + 4 + 1)*2;
        int insideHeight = boxHeight - offset - (3 + 4 + 1)*2;

        int insideX = (this.width  - boxWidth) /2          + 3 + 4 + 1;
        int insideY = (this.height - boxHeight)/2 + offset + 3 + 4 + 1;

        int paddingX = scaleX(7);
        int paddingY = scaleY(7);

        this.insideX = insideX;
        this.insideY = insideY;

        this.insideWidth = insideWidth;
        this.insideHeight = insideHeight;

        this.paddingX = paddingX;
        this.paddingY = paddingY;

        buttons.add(new ImAButton(
                -1,
                (this.width - boxWidth)/2, (this.height - boxHeight)/2,
                scaleX(175), scaleY(58),
                "Worlds"
        ) {
            @Override
            public void method_891(Minecraft minecraft, int i, int j) {
                super.method_891(minecraft, i, j);
            }

            @Override
            public void mouseReleased(int mouseX, int mouseY) {
                switchTab(new Worlds());
            }
        });

        switchTab(new Worlds());
    }

    private int paddingX;
    private int paddingY;

    private void switchTab(PlayTab tab) {
        this.tab = tab;

        tab.buttons = buttons;
        tab.mc = mc;
        tab.screen = this;

        tab.insideX = insideX;
        tab.insideY = insideY;

        tab.insideWidth = insideWidth;
        tab.insideHeight = insideHeight;

        tab.paddingX = paddingX;
        tab.paddingY = paddingY;

        tab.scrollY = 0;
        tab.init();
    }

    private int insideX, insideY,
                insideWidth, insideHeight;

    public final void startElementRendering(Minecraft mc, Runnable callback) {
        int scale = ((IMinecraft) mc).window().getScaleFactor();

        GL11.glScissor(
                insideX * scale,
                (PlayScreen.this.height - (insideY + insideHeight)) * scale,
                insideWidth * scale,
                insideHeight * scale
        );

        GL11.glTranslated(0, tab.scrollY, 0);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        callback.run();

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glTranslated(0, -tab.scrollY, 0);
    }

    public final void startElementRendering(Minecraft mc, int mouseY, Consumer<Integer> callback) {
        startElementRendering(mc, () -> {
            callback.accept(mouseY - tab.scrollY);
        });
    }

    private int scaleX(int value) {
        return (int) (value / 957F * width);
    }

    private int scaleY(int value) {
        return (int) (value / 508F * height);
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        ((ATitleScreen) titlescreen).bedrite$renderBackground(mouseX, mouseY, tickDelta);

        this.fillGradient(0, 0, this.width, this.height, -2130706433, 0xFFFFFF);
        this.fillGradient(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);

        super.render(mouseX, mouseY, tickDelta);
    }

    @Override
    public void tick() {
        super.tick();
        titlescreen.tick();
    }

    @Override
    public void method_1028(Minecraft minecraft, int i, int j) {
        super.method_1028(minecraft, i, j);
        titlescreen.method_1028(minecraft, i, j);
    }

    @Override
    public void handleMouse() {
        super.handleMouse();

        int boxWidth = scaleX(564);
        int boxHeight = scaleY(420);

        int offset = scaleY(53);

        int scale = ((IMinecraft) mc).window().getScaleFactor();
        int scroll = Mouse.getDWheel();

        int mouseX = Mouse.getX();
        int mouseY = PlayScreen.this.height*scale - Mouse.getY();

        if (
                mouseX >= ((this.width - boxWidth)/2           )*scale && mouseY >= ((this.height - boxHeight)/2 + offset   )*scale &&
                mouseX <= ((this.width - boxWidth)/2 + boxWidth)*scale && mouseY <= ((this.height - boxHeight)/2 + boxHeight)*scale
        ) {
            tab.scrollY += scroll / 100;
            tab.scrollY = Math.min(0, Math.max(-getMaxScrollY(), tab.scrollY));
        }
    }

    public final int getMaxScrollY() {
        int height = 0;
        for (Object obj : buttons) {
            ButtonWidget btn = (ButtonWidget) obj;

            height = Math.max(height, btn.y + ((AButtonWidget) btn).bedrite$height());
        }

        return height - this.height;
    }
}
