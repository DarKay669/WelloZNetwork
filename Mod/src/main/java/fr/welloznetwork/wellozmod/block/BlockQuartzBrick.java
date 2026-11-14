package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockQuartzBrick extends Block
{
    public BlockQuartzBrick()
    {
        super(Material.rock);
        setResistance(0.8F);
        setHardness(0.8F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("quartz_bricks");
        setBlockTextureName("wellozmod:quartz_bricks");
        setStepSound(soundTypePiston);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}