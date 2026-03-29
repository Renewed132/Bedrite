package pl.olafcio.bedrite.mixin;

import net.minecraft.item.FishingRodItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.olafcio.bedrite.Feature;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {
    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/FishingRodItem;setMaxDamage(I)Lnet/minecraft/item/Item;"), method = "<init>")
    @Feature("Set the fishing rod durability to 384, instead of 64")
    public void setMaxDamage(Args args) {
        args.set(0, 384);
    }
}
