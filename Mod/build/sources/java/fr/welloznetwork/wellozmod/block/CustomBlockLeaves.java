package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;

@Custom(BlockLeaves.class)
public class CustomBlockLeaves extends Block
{   
    public CustomBlockLeaves(String unlocalizedName, SoundType soundType)
    {
        super(Material.leaves);
        setHarvestLevel("sword", 0);
        setHardness(0.2F);
        setResistance(0.2F);
        setStepSound(soundType);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
}