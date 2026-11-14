package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMoss extends Block
{
    public BlockMoss()
    {
        super(Material.grass);
        setResistance(0.1F);
        setHardness(0.1F);
        setHarvestLevel("hoe", 0);
        setBlockName("moss_block");
        setBlockTextureName("wellozmod:moss_block");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_MOSS);
    }
}