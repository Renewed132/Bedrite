package pl.olafcio.bedrite.mixin.accessors;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TitleScreen.class)
public interface ATitleScreen {
    @Invoker("renderBackground")
    void bedrite$renderBackground(int mouseX, int mouseY, float tickDelta);
}
