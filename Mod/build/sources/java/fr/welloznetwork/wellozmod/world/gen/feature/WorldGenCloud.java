package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCloud extends WorldGenerator
{
    private int amount;
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        y += random.nextInt(64);
        
        for (int amount = 0; amount < this.amount; amount++)
        {
            boolean offsetY = random.nextBoolean();
            
            int xOffset = random.nextInt(2);
            int yOffset = (offsetY ? random.nextInt(3) - 1 : 0);
            int zOffset = random.nextInt(2);
            
            x += xOffset;
            y += yOffset;
            z += zOffset;
            
            for (int x1 = x; x1 < x + random.nextInt(2) + 3; x1++)
            {
                for (int y1 = y; y1 < y + random.nextInt(1) + 2; y1++)
                {
                    for (int z1 = z; z1 < z + random.nextInt(2) + 3; z1++)
                    {
                        if (world.isAirBlock(x1, y1, z1))
                        {
                            if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 + random.nextInt(2))
                            {
                                this.setBlockAndNotifyAdequately(world, x1, y1, z1, RegistryBlocks.cloud, 0);
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    public void setCloudAmount(int amount)
    {
        this.amount = amount;
    }
}