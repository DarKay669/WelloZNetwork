package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFoliage extends WorldGenerator
{
    private Block block;
    
    public WorldGenFoliage()
    {
        super();
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        for (int l = 0; l < 64; l++)
        {
            int x1 = (x + random.nextInt(8)) - random.nextInt(8);
            int y1 = (y + random.nextInt(4)) - random.nextInt(4);
            int z1 = (z + random.nextInt(8)) - random.nextInt(8);
            
            if (world.isAirBlock(x1, y1, z1) && ((BlockBush) this.block).canBlockStay(world, x1, y1, z1))
            {
                this.setBlockAndNotifyAdequately(world, x1, y1, z1, this.block, 0);
            }
        }
        
        return true;
    }
    
    public void setPlantBlock(Block block)
    {
        this.block = block;
    }
}