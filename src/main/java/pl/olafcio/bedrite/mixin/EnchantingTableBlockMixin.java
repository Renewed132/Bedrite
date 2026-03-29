package pl.olafcio.bedrite.mixin;

import net.minecraft.block.EnchantingTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.olafcio.bedrite.feature.BedriteMaterial;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockWithEntity;<init>(IILnet/minecraft/block/material/Material;)V"), method = "<init>")
    private static void init(Args args) {
        args.set(2, BedriteMaterial.STONE_NOTOOL);
    }
}
