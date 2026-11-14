package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockTorch;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockSoulTorch extends BlockTorch
{
    public BlockSoulTorch()
    {
        setLightLevel(0.625F);
        setBlockName("soul_torch");
        setBlockTextureName("wellozmod:soul_torch");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return Item.getItemFromBlock(RegistryBlocks.soul_torch);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        int l = world.getBlockMetadata(x, y, z);
        double d0 = (double) ((float) x + 0.5F);
        double d1 = (double) ((float) y + 0.7F);
        double d2 = (double) ((float) z + 0.5F);
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;
        
        if (l == 1)
        {
            world.spawnParticle("explode", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        }
        
        else if (l == 2)
        {
            world.spawnParticle("explode", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        }
        
        else if (l == 3)
        {
            world.spawnParticle("explode", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        }
        
        else if (l == 4)
        {
            world.spawnParticle("explode", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        }
        
        else
        {
            world.spawnParticle("explode", d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}