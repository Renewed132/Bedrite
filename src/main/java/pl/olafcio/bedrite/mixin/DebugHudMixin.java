package pl.olafcio.bedrite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import pl.olafcio.renewed.mixinclass.DebugHud;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Shadow
    public Minecraft mc;

    @WrapMethod(method = "render")
    public void render(float partialTicks, boolean inScreen, int mouseX, int mouseY, Operation<Void> original) {
        GL11.glTranslatef(-10F, -10F, 0F);
        original.call(partialTicks, inScreen, mouseX, mouseY);
        GL11.glTranslatef(10F, 10F, 0F);
    }

    /**
     * @author Olafcio
     * @reason MC Bedrock Edition doesn't have a related F3 text drawer.
     */
    @Overwrite
    private void draw(String text, int x, int y) {
        TextRenderer textRen = this.mc.textRenderer;
        textRen.method_956(text, x, y, -2039584);
    }
}
