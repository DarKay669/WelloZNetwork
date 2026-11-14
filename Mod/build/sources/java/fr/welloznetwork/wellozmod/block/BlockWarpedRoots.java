package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockBush;

public class BlockWarpedRoots extends BlockBush
{
    public BlockWarpedRoots()
    {
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("warped_roots");
        setBlockTextureName("wellozmod:warped_roots");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(soundTypeGrass);
    }
}