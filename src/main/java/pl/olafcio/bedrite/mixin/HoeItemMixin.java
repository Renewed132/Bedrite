package pl.olafcio.bedrite.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import pl.olafcio.bedrite.Feature;
import pl.olafcio.renewed.ShouldBeNamed;

@Mixin(HoeItem.class)
public class HoeItemMixin extends Item {
    @Shadow protected ToolMaterial material;

    protected HoeItemMixin(int i) {
        super(i);
    }

    @Override
    @Feature("Change hoes attack damage to 2+MaterialModifier")
    @ShouldBeNamed("getAttackDamage")
    public int method_3347(Entity entity) {
        return 2 + material.method_3401();  // getAttackDamage
    }
}
