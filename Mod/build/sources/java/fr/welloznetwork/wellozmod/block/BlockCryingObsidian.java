package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCryingObsidian extends Block
{
    public BlockCryingObsidian()
    {
        super(Material.rock);
        setResistance(1200.0F);
        setHardness(50.0F);
        setLightLevel(1.0F);
        setHarvestLevel("pickaxe", 3);
        setBlockName("crying_obsidian");
        setBlockTextureName("wellozmod:crying_obsidian");
        setStepSound(soundTypePiston);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}