package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockIcedStone extends Block
{
    public BlockIcedStone()
    {
        super(Material.rock);
        setHarvestLevel("pickaxe", 0);
        setHardness(1.3F);
        setStepSound(soundTypeStone);
        setBlockName("iced_stone");
        setBlockTextureName("wellozmod:iced_stone");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}