package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBlockSplatter extends WorldGenerator
{
    private Block block, replaceBlock;
    private int metadata, replaceMetadata, size;
    
    public WorldGenBlockSplatter(Block block, int metadata, Block replaceBlock, int replaceMetadata, int size)
    {
        super(true);
        
        this.block = block;
        this.metadata = metadata;
        this.replaceBlock = replaceBlock;
        this.replaceMetadata = replaceMetadata;
        this.size = size;
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        while (y-- > 0)
        {
            if (!world.isAirBlock(x, y, z))
            {
                break;
            }
        }
        
        if (y <= 0)
        {
            return false;
        }
        
        if (world.getBlock(x, y, z) == this.replaceBlock && world.getBlockMetadata(x, y, z) == this.replaceMetadata)
        {
            for (int i = 0; i < this.size * this.size * this.size; i++)
            {
                int i1 = x + this.nextInt(random, 0, this.size);
                int j1 = y + this.nextInt(random, 0, 4);
                int k1 = z + this.nextInt(random, 0, this.size);
                
                if (world.isAirBlock(i1, j1 + 1, k1) && world.getBlock(i1, j1, k1) == this.replaceBlock && world.getBlockMetadata(i1, j1, k1) == this.replaceMetadata)
                {
                    world.setBlock(i1, j1, k1, this.block, this.metadata, 2);
                }
            }
        }
        
        else
        {
            return false;
        }
        
        return true;
    }
    
    private int nextInt(Random random, int peak, int size, int lowerTrunc, int upperTrunc)
    {
        float sample;
        
        do
        {
            float random1 = random.nextFloat();
            float random2 = random.nextFloat();
            
            sample = (random1 - random2) * (size + 0.5F) + peak;
        }
        
        while (sample < lowerTrunc - 0.5F || sample > upperTrunc + 0.5F);
        
        return Math.round(sample);
    }
    
    private int nextInt(Random random, int peak, int size)
    {
        return this.nextInt(random, peak, size, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}