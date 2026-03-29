package pl.olafcio.bedrite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import pl.olafcio.renewed.features.NewBlock;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends Entity {
    public SpiderEntityMixin(World world) {
        super(world);
    }

    @WrapMethod(method = "method_2660")
    public boolean canClimb(Operation<Boolean> original) {
        if (!original.call())
            return false;

        int var1 = MathHelper.floor(this.x);
        int var2 = MathHelper.floor(this.boundingBox.minY);
        int var3 = MathHelper.floor(this.z);
        int var4 = this.world.getBlock(var1, var2, var3);
        return var4 != NewBlock.MAGMA.id;
    }
}
