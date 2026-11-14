package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenBasaltPillars;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeHellSoulSandValleyDecorator extends BiomeHellDecorator
{
    private WorldGenerator crimsonGrassGenerator, pillarGenerator;
    
    public BiomeHellSoulSandValleyDecorator()
    {
        this.crimsonGrassGenerator = new WorldGenFlowers(RegistryBlocks.crimson_roots);
        this.pillarGenerator = new WorldGenBasaltPillars();
    }
    
    public void populate(World world, Random random, int chunkX, int chunkZ)
    {
        if (random.nextInt(3) == 0)
        {
            int x = chunkX + random.nextInt(16) + 8;
            int y = MathHelper.getRandomIntegerInRange(random, 32, world.provider.getActualHeight() - 8);
            int z = chunkZ + random.nextInt(16) + 8;
            
            this.crimsonGrassGenerator.generate(world, random, x, y, z);
        }
    }
    
    public void decorate(World world, Random random, int chunkX, int chunkZ)
    {
        if (random.nextInt(6) == 0)
        {
            int x = chunkX + random.nextInt(16) + 8;
            int y = MathHelper.getRandomIntegerInRange(random, 32, world.provider.getActualHeight() - 8);
            int z = chunkZ + random.nextInt(16) + 8;
            
            while (y++ <= world.provider.getActualHeight() - 8)
            {
                if (!world.isAirBlock(x, y, z))
                {
                    break;
                }
            }
            
            this.pillarGenerator.generate(world, random, x, y - 1, z);
        }
    }
}