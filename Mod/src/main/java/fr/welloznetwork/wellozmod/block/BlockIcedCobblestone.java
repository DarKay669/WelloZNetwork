package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockIcedCobblestone extends Block
{
    public BlockIcedCobblestone()
    {
        super(Material.rock);
        setHarvestLevel("pickaxe", 0);
        setHardness(1.1F);
        setStepSound(soundTypeStone);
        setBlockName("iced_cobblestone");
        setBlockTextureName("wellozmod:iced_cobblestone");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}