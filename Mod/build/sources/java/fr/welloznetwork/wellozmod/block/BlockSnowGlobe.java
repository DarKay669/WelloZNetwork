package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.tileentity.TileEntitySnowGlobe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSnowGlobe extends BlockContainer
{
    private int tick = 0;
    
    public BlockSnowGlobe()
    {
        super(Material.glass);
        setHardness(0.5F);
        setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
        setStepSound(soundTypeGlass);
        setBlockName("snow_globe");
        setBlockTextureName("wellozmod:snow_globe");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public void randomDisplayTick(World world, int x, int y, int z, Random r)
    {
        double d0 = x + 0.6D, d1 = y + 0.3D, d2 = z + 0.325D, d3 = 0.0199999988079071D, d4 = 27000001072883606D;
        super.randomDisplayTick(world, x, y, z, r);
        
        if (this.tick > 0)
        {
            this.tick--;
        }
        
        for (int l = x - 2; l <= x + 2; l++)
        {
            for (int i1 = z - 2; i1 <= z + 2; i1++)
            {
                if (l > x - 2 && l < x + 2 && i1 == z - 1)
                {
                    i1 = z + 2;
                }
                
                if (r.nextInt(30) == 0 && world.isRaining())
                {
                    for (int j1 = y; j1 <= y + 1; j1++)
                    {
                        world.spawnParticle("snowshovel", d0 - (r.nextInt(8) / 10) - 0.1D, d1 + d3, d2 + d4 - (r.nextInt(8) / 10) - 0.1D, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }
    
    public int getRenderType()
    {
        return -2;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntitySnowGlobe();
    }
}