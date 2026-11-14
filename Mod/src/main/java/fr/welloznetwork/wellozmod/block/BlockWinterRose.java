package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BlockWinterRose extends BlockBush
{
    public BlockWinterRose()
    {
        super(Material.plants);
        setTickRandomly(true);
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("winter_rose");
        setBlockTextureName("wellozmod:winter_rose");
        setBlockBounds(0.5F - 0.2F, 0.0F, 0.5F - 0.2F, 0.5F + 0.2F, 0.2F * 3.0F, 0.5F + 0.2F);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.snow;
    }
}