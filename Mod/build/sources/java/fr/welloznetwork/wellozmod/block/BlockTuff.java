package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTuff extends Block
{
    public BlockTuff()
    {
        super(Material.rock);
        setResistance(6.0F);
        setHardness(1.5F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("tuff");
        setBlockTextureName("wellozmod:tuff");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_TUFF);
    }
}