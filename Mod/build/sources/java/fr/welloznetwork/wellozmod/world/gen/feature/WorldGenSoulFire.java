package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSoulFire extends WorldGenerator
{
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        for (int i = 0; i < 64; i++)
        {
            int x1 = x + random.nextInt(8);
            int y1 = y + random.nextInt(4);
            int z1 = z + random.nextInt(8);
            
            if (world.isAirBlock(x1, y1, z1) && world.getBlock(x1, y1 - 1, z1) == RegistryBlocks.warped_nylium)
            {
                world.setBlock(x1, y1, z1, RegistryBlocks.soul_fire, 0, 2);
            }
        }
        
        return true;
    }
}