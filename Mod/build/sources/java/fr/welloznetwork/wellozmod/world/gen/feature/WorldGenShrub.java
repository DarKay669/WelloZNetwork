package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenShrub extends WorldGenTrees
{
    private Block wood, leaves;
    private int metaWood, metaLeaves, minHeight, maxHeight;
    private List soilBlocks;
    
    public WorldGenShrub(Block wood, Block leaves, int metaWood, int metaLeaves, Block... soilBlocks)
    {
        this(wood, leaves, metaWood, metaLeaves, 0, 256, soilBlocks);
    }
    
    public WorldGenShrub(Block wood, Block leaves, int metaWood, int metaLeaves, int minHeight, int maxHeight, Block... soilBlocks)
    {
        super(false);
        
        this.wood = wood;
        this.leaves = leaves;
        this.metaWood = metaWood;
        this.metaLeaves = metaLeaves;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.soilBlocks = Arrays.asList(soilBlocks);
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        Block block;
        
        do
        {
            block = world.getBlock(x, y, z);
            
            if (!(block.isLeaves(world, x, y, z) || block.isAir(world, x, y, z)))
            {
                break;
            }
            
            y--;
        }
        
        while (y > 0);
        
        Block block1 = world.getBlock(x, y, z);
        
        if (soilBlocks.contains(block1))
        {
            y++;
            
            if (y > minHeight && y < maxHeight)
            {
                this.setBlockAndNotifyAdequately(world, z, x, y, wood, metaWood);
                
                for (int l = y; l <= y + 2; l++)
                {
                    int i1 = l - y;
                    int j1 = 2 - i1;
                    
                    for (int k1 = x - j1; k1 <= x + j1; k1++)
                    {
                        int l1 = k1 - x;
                        
                        for (int i2 = z - j1; i2 <= z + j1; i2++)
                        {
                            int j2 = i2 - z;
                            
                            if ((Math.abs(l1) != j1 || Math.abs(j2) != j1 || random.nextInt(2) != 0) && world.getBlock(k1, l, i2).canBeReplacedByLeaves(world, k1, l, i2))
                            {
                                this.setBlockAndNotifyAdequately(world, k1, l, i2, leaves, metaLeaves);
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }
}