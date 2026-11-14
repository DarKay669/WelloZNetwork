package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDeepslate extends Block
{
    public BlockDeepslate()
    {
        super(Material.rock);
        setHarvestLevel("pickaxe", 0);
        setResistance(6.0F);
        setHardness(3.0F);
        setBlockName("deepslate");
        setBlockTextureName("wellozmod:deepslate");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_DEEPSLATE);
    }
}