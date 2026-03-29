package pl.olafcio.bedrite.mixin;

import net.minecraft.block.FurnaceBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.olafcio.bedrite.feature.BedriteMaterial;

@Mixin(FurnaceBlock.class)
public class FurnaceBlockMixin {
    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockWithEntity;<init>(ILnet/minecraft/block/material/Material;)V"), method = "<init>")
    private static void init(Args args) {
        args.set(1, BedriteMaterial.SMELTER);
    }
}
