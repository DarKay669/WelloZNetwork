package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHellGrass extends WorldGenerator
{
    private boolean crimson;
    
    public WorldGenHellGrass(boolean crimson)
    {
        this.crimson = crimson;
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        do
        {
            if (!world.isAirBlock(x, y, z))
            {
                break;
            }
            
            y--;
        }
        
        while (y > 0);
        
        if (y <= 0)
        {
            return false;
        }
        
        for (int i = 0; i < 128; i++)
        {
            Block grass;
            int metadata;
            
            if (this.crimson)
            {
                metadata = 0;
                
                if (random.nextInt(32) == 0)
                {
                    grass = RegistryBlocks.crimson_fungus;
                    
                    if (random.nextInt(16) == 0)
                    {
                        metadata = 1;
                    }
                }
                
                else
                {
                    grass = RegistryBlocks.crimson_roots;
                }
            }
            
            else
            {
                metadata = 1;
                
                if (random.nextInt(32) == 0)
                {
                    grass = RegistryBlocks.warped_fungus;
                    
                    if (random.nextInt(16) == 0)
                    {
                        metadata = 0;
                    }
                }
                
                else
                {
                    if (random.nextInt(3) == 0)
                    {
                        grass = RegistryBlocks.nether_sprouts;
                        metadata = 0;
                    }
                    
                    else
                    {
                        grass = RegistryBlocks.warped_roots;
                        
                        if (random.nextInt(32) == 0)
                        {
                            metadata = 0;
                        }
                    }
                }
            }
            
            int i1 = x + random.nextInt(8) - random.nextInt(8);
            int j1 = y + random.nextInt(4) - random.nextInt(4);
            int k1 = z + random.nextInt(8) - random.nextInt(8);
            
            if (world.isAirBlock(i1, j1, k1) && grass.canBlockStay(world, i1, j1, k1))
            {
                world.setBlock(i1, j1, k1, grass, metadata, 2);
            }
        }
        
        return true;
    }
}