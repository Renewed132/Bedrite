package pl.olafcio.bedrite.mixin;

import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.bedrite.Feature;

@Mixin(value = MobEntity.class, priority = 1500)
public class MobEntityMixin {
    @Inject(at = @At("HEAD"), method = "getVoidDamageDelay", cancellable = true)  // method from Renewed
    @Feature("Change void damage delay to 40ticks[/2s] instead of 1tick[/0.05s]")
    @SuppressWarnings("all")
    public void getVoidDamageDelay(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(-40);
    }
}
