package pl.olafcio.bedrite.mixin;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.bedrite.Feature;

@Mixin(value = MobEntity.class, priority = 1500)
public class MobEntityMixin {
    @Shadow @Final protected GoalSelector attackGoals;

    @Inject(at = @At("HEAD"), method = "getVoidDamageDelay", cancellable = true)  // method from Renewed
    @Feature("Change void damage delay to 40ticks[/2s] instead of 1tick[/0.05s]")
    @SuppressWarnings("all")
    public void getVoidDamageDelay(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(-40);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;dropInventory(Lnet/minecraft/entity/damage/DamageSource;)V"), method = "damage")
    @Feature("Wolf aggressive when another wolf insta-killed")
    public void damage__killed(DamageSource source, int damage, CallbackInfoReturnable<Boolean> cir) {
        for (int i = 0; i < 2; i++)
            attackGoals.tick();
    }
}
