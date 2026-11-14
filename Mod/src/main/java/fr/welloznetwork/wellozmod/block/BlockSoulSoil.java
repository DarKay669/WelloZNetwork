package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSoulSoil extends Block
{
    public BlockSoulSoil()
    {
        super(Material.rock);
        setHarvestLevel("shovel", 0);
        setResistance(0.5F);
        setHardness(0.5F);
        setBlockName("soul_soil");
        setBlockTextureName("wellozmod:soul_soil");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_SOUL_SOIL);
    }
}