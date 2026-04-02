package pl.olafcio.bedrite.mixin;

import net.minecraft.world.chunk.FlatChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FlatChunkGenerator.class)
public class FlatChunkGeneratorMixin {
    @ModifyConstant(constant = {
            @Constant(intValue = 256)
    }, method = "method_3997")
    public int generationYlimit(int constant) {
        return 129;
    }
}
