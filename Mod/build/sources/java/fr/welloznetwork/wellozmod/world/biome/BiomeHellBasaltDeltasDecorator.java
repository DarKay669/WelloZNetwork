package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBiomes;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenBasaltColumns;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenDeltas;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeHellBasaltDeltasDecorator extends BiomeHellDecorator
{
    private WorldGenerator deltaGenerator, smallBasaltColumnGenerator, largeBasaltColumnGenerator;
    
    public BiomeHellBasaltDeltasDecorator()
    {
        this.deltaGenerator = new WorldGenDeltas();
        this.smallBasaltColumnGenerator = new WorldGenBasaltColumns(1, 4, 1, 1);
        this.largeBasaltColumnGenerator = new WorldGenBasaltColumns(5, 10, 2, 3);
    }
    
    public void populate(World world, Random random, int chunkX, int chunkZ)
    {
        for (int attempt = 0; attempt < 15; attempt++)
        {
            int x = chunkX + random.nextInt(16) + 8;
            int y = MathHelper.getRandomIntegerInRange(random, 32, world.provider.getActualHeight() - 24);
            int z = chunkZ + random.nextInt(16) + 8;
            
            while (y-- > 32)
            {
                if (!world.isAirBlock(x, y, z))
                {
                    break;
                }
            }
            
            this.deltaGenerator.generate(world, random, x, y, z);
        }
        
        for (int segment = 0; segment < 5; segment++)
        {
            for (int attempt = 0; attempt < 12; attempt++)
            {
                int x = chunkX + random.nextInt(16) + 8;
                int y = MathHelper.getRandomIntegerInRange(random, 32 + (segment * 18), 54 + (segment * 18));
                int z = chunkZ + random.nextInt(16) + 8;
                
                if (world.getBiomeGenForCoords(x, z) != RegistryBiomes.BASALT_DELTAS)
                {
                    continue;
                }
                
                while (y-- > 32)
                {
                    if (!world.isAirBlock(x, y - 1, z))
                    {
                        break;
                    }
                }
                
                boolean useSmallGen = random.nextInt(4) == 0 || world.getBiomeGenForCoords(x + 16, z) != RegistryBiomes.BASALT_DELTAS || world.getBiomeGenForCoords(x, z + 16) != RegistryBiomes.BASALT_DELTAS || world.getBiomeGenForCoords(x + 16, z + 16) != RegistryBiomes.BASALT_DELTAS || world.getBiomeGenForCoords(x - 16, z) != RegistryBiomes.BASALT_DELTAS ||world.getBiomeGenForCoords(x, z - 16) != RegistryBiomes.BASALT_DELTAS || world.getBiomeGenForCoords(x - 16, z - 16) != RegistryBiomes.BASALT_DELTAS;;
                
                if (world.getBlock(x, y - 1, z) != RegistryBlocks.basalt)
                {
                    (useSmallGen ? this.smallBasaltColumnGenerator : this.largeBasaltColumnGenerator).generate(world, random, x, y, z);
                }
            }
        }
    }
    
    public void decorate(World world, Random random, int chunkX, int chunkZ) {}
}