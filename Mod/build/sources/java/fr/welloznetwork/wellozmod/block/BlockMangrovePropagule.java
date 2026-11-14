package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;

public class BlockMangrovePropagule extends BlockBush
{
    public BlockMangrovePropagule()
    {
        super(Material.plants);
        setTickRandomly(true);
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("mangrove_propagule");
        setBlockTextureName("wellozmod:mangrove_propagule");
        setBlockBounds(0.5F - 0.2F, 0.0F, 0.5F - 0.2F, 0.5F + 0.2F, 0.2F * 3.0F, 0.5F + 0.2F);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}