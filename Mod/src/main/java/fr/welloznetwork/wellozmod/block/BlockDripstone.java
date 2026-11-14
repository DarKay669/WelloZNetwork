package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDripstone extends Block
{
    public BlockDripstone()
    {
        super(Material.rock);
        setResistance(1.0F);
        setHardness(1.5F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("dripstone_block");
        setBlockTextureName("wellozmod:dripstone_block");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_DRIPSTONE);
    }
}