package pl.olafcio.bedrite.feature;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BedriteMaterial extends Material {
    public static final BedriteMaterial STONE_NOTOOL = new BedriteMaterial(MaterialColor.STONE);

    public BedriteMaterial(MaterialColor materialColor) {
        super(materialColor);
    }
}
