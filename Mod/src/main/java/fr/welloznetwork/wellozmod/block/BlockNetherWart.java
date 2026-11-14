package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockNetherWart extends Block
{
    public BlockNetherWart()
    {
        super(Material.grass);
        setResistance(5.0F);
        setHardness(1.0F);
        setBlockName("nether_wart");
        setBlockTextureName("wellozmod:nether_wart");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean isLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        return true;
    }
}