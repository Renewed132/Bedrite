package pl.olafcio.bedrite.mixin.accessors;

import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface AWorldRenderer {
    @Accessor("field_1896")
    int have();

    @Accessor("field_1893")
    int all();
}
