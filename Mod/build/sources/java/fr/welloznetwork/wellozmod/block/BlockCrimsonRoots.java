package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockBush;

public class BlockCrimsonRoots extends BlockBush
{
    public BlockCrimsonRoots()
    {
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("crimson_roots");
        setBlockTextureName("wellozmod:crimson_roots");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(soundTypeGrass);
    }
}