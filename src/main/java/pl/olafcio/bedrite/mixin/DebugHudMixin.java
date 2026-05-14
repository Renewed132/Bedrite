package pl.olafcio.bedrite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import pl.olafcio.bedrite.mixin.accessors.IWorldRenderer;
import pl.olafcio.bedrite.util.StringUtil;
import pl.olafcio.renewed.mixin.accessors.IClientPlayerInteractionManager;
import pl.olafcio.renewed.mixin.accessors.IMinecraft;
import pl.olafcio.renewed.mixinclass.DebugHud;

import java.lang.management.ManagementFactory;

/**
 * Based on <a href="https://www.reddit.com/r/Minecraft/comments/tpl2j7/reminder_that_an_actual_f3_screen_does_exist_on/">this Reddit post</a>.
 */
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
     * @reason MC Bedrock Edition doesn't have a related left text.
     */
    @Overwrite
    public String[] getLeft(Chunk chunk, int x, int y, int z) {
        int skyLight = this.mc.world.method_3642(LightType.SKY, x, y, z);
        int blockLight = this.mc.world.method_3642(LightType.BLOCK, x, y, z);

        return new String[]{
                "Minecraft v" + FabricLoader.getInstance().getRawGameVersion(),
                "Mode: " + StringUtil.capitalize(((IClientPlayerInteractionManager) mc.interactionManager).gameMode().getGameModeName()),
                String.format("XYZ: %.3f / %.5f / %.3f", mc.playerEntity.x, mc.playerEntity.y, mc.playerEntity.z),
                String.format("Camera XYZ: %.3f / %.5f / %.3f", mc.cameraEntity.x, mc.cameraEntity.y, mc.cameraEntity.z),
                // Camera Target XYZ: %.3f / %.5f / %.3f
                // Player Forward Ref XYZ: %.3f / %.5f / %.3f
                // Camera Forward XYZ: %.3f / %.5f / %.3f
                String.format("Block: %.0f %.0f %.0f", mc.playerEntity.x, mc.playerEntity.y, mc.playerEntity.z),
                String.format("Chunk: %d %d %d in %d %d %d", 0,0,0,0,0,0),
                String.format("Facing: ? (Towards negative/positive X/Y/Z) (?.? / ?.?)"),
                String.format("Speed: %.2f m/s", mc.playerEntity.horizontalSpeed),
                String.format("Render Chunks: %d / %d", ((IWorldRenderer) mc.worldRenderer).have(), ((IWorldRenderer) mc.worldRenderer).all()),
                String.format("Biome: %s", chunk.getBiome(x & 0xF, z & 0xF, this.mc.world.getBiomeSource()).name),
                String.format("Base light: %d (%d sky, %d block)", Math.min(15, skyLight + blockLight), skyLight, blockLight),
                String.format("Light: %d (%d sky, %d block)", Math.min(15, skyLight + blockLight), skyLight, blockLight),
                String.format("Difficulty: %d (Day ?)", mc.world.difficulty),
                String.format("Time of Day: %d Time in Moon Month: ?", mc.world.getTimeOfDay()),
                String.format("Time in World: %d", mc.world.getTimeOfDay()),
                String.format("Frame time: %f ms", 0f),
                String.format("Frame rate: %d fps", ((IMinecraft) mc).fpsCounter()),
                String.format("Chunk rebuilds: %d/s, dirty: %d", 0, 0),
                String.format("Server Chunks Ticked: %d", 0),
                String.format("Client Chunks Ticked: %d", 0),
                String.format("Server Entity Ticks: %d Players, %d Monsters, %d Animals, %d Villagers, %d Other", 0,0,0,0,0),
                String.format("Client Entity Ticks: %d Players, %d Monsters, %d Animals, %d Villagers, %d Other", 0,0,0,0,0),
                String.format("Sent (zip/raw): %.1f/%.1f KB/s (100%%)", 0f, 0f),
                String.format("Received (zip/raw): %.1f/%.1f KB/s (100%%)", 0f, 0f)
        };
    }

    /**
     * @author Olafcio
     * @reason MC Bedrock Edition doesn't have a related right text.
     */
    @Overwrite
    public String[] getRight(long memUsed, long memMax, long memTotal) {
        return new String[]{
                String.format("OS: %s", ManagementFactory.getOperatingSystemMXBean().getVersion()),
                "Java: " + System.getProperty("java.version") + " " + System.getProperty("sun.arch.data.model") + "bit",
                "Mem: " + memUsed * 100L / memMax + "% " + memUsed / 1024L / 1024L + "/" + memMax / 1024L / 1024L + "MB",
                "Allocated: " + memTotal * 100L / memMax + "% " + memTotal / 1024L / 1024L + "MB",
                "",
                "Display: " + this.mc.width + "x" + this.mc.height,
                "GPU: " + GL11.glGetString(GL11.GL_RENDERER),
                "GPU Dedicated Memory: ?",
                "MSAA: ?",
                String.format("Render Distance: %d", mc.options.renderDistance),
                "",
                "",
                "",
                "Main Scene Stack",
                "[cubemap_background_screen]",
                "[debug_screen]",
                "[toast_screen]",
                "",
                "Client Scene Stack",
                "[in_game_play_screen]",
                "[hud_screen]",
                "",
                "Router History",
                "[/]"
        };
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
