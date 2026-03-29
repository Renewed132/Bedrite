package pl.olafcio.bedrite.feature;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import pl.olafcio.bedrite.Feature;

public class BedriteMaterial extends Material {
    @Feature("Make furnaces and enchanting tables drop when mined by hand")
    public static final BedriteMaterial STONE_NOTOOL = new BedriteMaterial(MaterialColor.STONE);

    public BedriteMaterial(MaterialColor materialColor) {
        super(materialColor);
    }
}
