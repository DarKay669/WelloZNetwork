package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenHugeTree extends WorldGenAbstractTree
{
    public WorldGenHugeTree(boolean notify)
    {
        super(notify);
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        int height = 25 + random.nextInt(6);
        
        if (!world.getBlock(x, y, z).canSustainPlant(world, x, y, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling))
        {
            return false;
        }
        
        for (int i = 0; i < height; i++)
        {
            world.setBlock(x, y + i, z, Blocks.log, 0, 2);
        }
        
        for (int x1 = -3; x1 <= 3; x1++)
        {
            for (int y1 = -3; y1 <= 3; y1++)
            {
                for (int z1 = -3; z1 <= 3; z1++)
                {
                    if (x1 * x1 + y1 * y1 + z1 * z1 <= 3 * 3)
                    {
                        int x2 = x + x1;
                        int y2 = y + height + y1;
                        int z2 = z + z1;
                        
                        if (world.getBlock(x2, y2, z2).isAir(world, x2, y2, z2) || world.getBlock(x2, y2, z2).isLeaves(world, x2, y2, z2))
                        {
                            world.setBlock(x2, y2, z2, Blocks.leaves, 0, 2);
                        }
                    }
                }
            }
        }
        
        return true;
    }
}