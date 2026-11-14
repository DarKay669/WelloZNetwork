package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBasaltPillars extends WorldGenerator
{
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        if (world.isAirBlock(x, y, z) && !world.isAirBlock(x, y + 1, z))
        {
            int mutableX2;
            int mutableY1 = y, mutableY2;
            int mutableZ2;
            
            boolean flag = true;
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;
            
            while (world.isAirBlock(x, mutableY1, z))
            {
                world.setBlock(x, mutableY1, z, RegistryBlocks.basalt);
                
                mutableX2 = x - 1;
                mutableY2 = mutableY1;
                mutableZ2 = z;
                
                flag = flag && this.placeHangOff(world, random, mutableX2, mutableY2, mutableZ2);
                
                mutableX2 = x + 1;
                
                flag1 = flag1 && this.placeHangOff(world, random, mutableX2, mutableY2, mutableZ2);
                
                mutableX2 = x;
                mutableZ2 = z - 1;
                
                flag2 = flag2 && this.placeHangOff(world, random, mutableX2, mutableY2, mutableZ2);
                
                mutableZ2 = z + 1;
                
                flag3 = flag3 && this.placeHangOff(world, random, mutableX2, mutableY2, mutableZ2);
                
                mutableY1--;
            }
            
            mutableY1++;
            mutableX2 = x - 1;
            mutableY2 = mutableY1;
            mutableZ2 = z;
            
            this.placeBaseHangOff(world, random, mutableX2, mutableY2, mutableZ2);
            
            mutableX2 = x + 1;
            
            this.placeBaseHangOff(world, random, mutableX2, mutableY2, mutableZ2);
            
            mutableX2 = x;
            mutableZ2 = z - 1;
            
            this.placeBaseHangOff(world, random, mutableX2, mutableY2, mutableZ2);
            
            mutableZ2 = z + 1;
            
            this.placeBaseHangOff(world, random, mutableX2, mutableY2, mutableZ2);
            
            mutableY1--;
            
            int mutableX3;
            int mutableY3;
            int mutableZ3;
            
            for (int i = -3; i < 4; i++)
            {
                for (int j = -3; j < 4; j++)
                {
                    int k = Math.abs(i) * Math.abs(j);
                    
                    if (random.nextInt(10) < 10 - k)
                    {
                        mutableX3 = x + i;
                        mutableY3 = mutableY1;
                        mutableZ3 = z + j;
                        
                        int l = 3;
                        
                        mutableX2 = mutableX3;
                        mutableY2 = mutableY3 - 1;
                        mutableZ2 = mutableZ3;
                        
                        while (world.isAirBlock(mutableX2, mutableY2, mutableZ2))
                        {
                            mutableY3--;
                            l--;
                            
                            if (l <= 0)
                            {
                                break;
                            }
                        }
                        
                        mutableY2 = mutableY3 - 1;
                        
                        if (!world.isAirBlock(mutableX2, mutableY2, mutableZ2))
                        {
                            world.setBlock(mutableX3, mutableY3, mutableZ3, RegistryBlocks.basalt, 0, 2);
                        }
                    }
                }
            }
            
            return true;
        }
        
        else
        {
            return false;
        }
    }
    
    private void placeBaseHangOff(World world, Random random, int x, int y, int z)
    {
        if (random.nextBoolean())
        {
            world.setBlock(x, y, z, RegistryBlocks.basalt, 0, 2);
        }
    }
    
    private boolean placeHangOff(World world, Random random, int x, int y, int z)
    {
        if (random.nextInt(10) != 0)
        {
            world.setBlock(x, y, z, RegistryBlocks.basalt, 0, 2);
            return true;
        }
        
        else
        {
            return false;
        }
    }
}