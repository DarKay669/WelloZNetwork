package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenEndGateway extends WorldGenerator
{
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        int xFrom = x - 1, yFrom = y - 2, zFrom = z -1, xTo = x + 1, yTo = y + 2, zTo = z + 1;
        
        for (int genX = xFrom; genX <= xTo; genX++)
        {
            for (int genY = yFrom; genY <= yTo; genY++)
            {
                for (int genZ = zFrom; genZ <= zTo; genZ++)
                {
                    boolean flag = genX == x;
                    boolean flag1 = genY == y;
                    boolean flag2 = genZ == z;
                    boolean flag3 = Math.abs(genY - y) == 2;
                    
                    if (flag && flag1 && flag2)
                    {
                        world.setBlock(genX, genY, genZ, RegistryBlocks.barrier); //end_gateway
                    }
                    
                    else if (flag1)
                    {
                        world.setBlock(genX, genY, genZ, Blocks.air);
                    }
                    
                    else if (flag3 && flag && flag2)
                    {
                        world.setBlock(genX, genY, genZ, Blocks.bedrock);
                    }
                    
                    else if ((flag || flag2) && !flag3)
                    {
                        world.setBlock(genX, genY, genZ, Blocks.bedrock);
                    }
                    
                    else
                    {
                        world.setBlock(genX, genY, genZ, Blocks.air);
                    }
                }
            }
        }
        
        return true;
    }
}