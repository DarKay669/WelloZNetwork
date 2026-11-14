package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMushroomStem extends Block
{
    public BlockMushroomStem()
    {
        super(Material.rock);
        setHarvestLevel("axe", 0);
        setResistance(0.2F);
        setHardness(0.2F);
        setBlockName("mushroom_stem");
        setBlockTextureName("wellozmod:mushroom_stem");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}