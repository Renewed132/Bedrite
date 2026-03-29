package pl.olafcio.bedrite.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.DyeItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlock(III)I", ordinal = 0), method = "method_3355")
    public int use__getBlock(World world, int x, int y, int z) {
        int block = world.getBlock(x, y, z);
        if (block == Block.SUGARCANE.id) {
            for (int i = 0; i < 2; i++, y++) {
                world.method_3672(x, y, z, 15); // setBlockData - grow
                Block.SUGARCANE.onTick(world, x, y, z, world.random);
            }
        }

        return block;
    }
}
