package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;

@Custom(BlockStone.class)
public class CustomBlockStone extends Block
{
    public CustomBlockStone(String unlocalizedName)
    {
        super(Material.rock);
        setHardness(1.5F);
        setResistance(6.0F);
        setHarvestLevel("pickaxe", 0);
        setStepSound(soundTypeStone);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}