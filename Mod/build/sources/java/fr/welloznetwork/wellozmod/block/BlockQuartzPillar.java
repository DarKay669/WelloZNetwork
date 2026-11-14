package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockQuartzPillar extends Block
{
    public BlockQuartzPillar()
    {
        super(Material.rock);
        setResistance(0.8F);
        setHardness(0.8F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("quartz_pillar");
        setBlockTextureName("wellozmod:quartz_pillar");
        setStepSound(soundTypePiston);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}