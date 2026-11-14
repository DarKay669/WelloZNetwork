package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMud extends Block
{
    public BlockMud()
    {
        super(Material.clay);
        setResistance(0.5F);
        setHardness(0.5F);
        setHarvestLevel("shovel", 0);
        setBlockName("mud");
        setBlockTextureName("wellozmod:mud");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_MUD);
    }
}