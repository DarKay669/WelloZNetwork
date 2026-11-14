package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;

public class BlockEndBricksSlab extends BlockSlab
{
    public BlockEndBricksSlab()
    {
        super(false, Material.rock);
        setHardness(3.0F);
        setBlockName("end_bricks_slab");
        setBlockTextureName("wellozmod:end_bricks");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }

    public String func_150002_b(int p_150002_1_)
    {
        return null;
    }
}