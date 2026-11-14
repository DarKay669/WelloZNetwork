package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import fr.welloznetwork.wellozmod.block.BlockCrimsonNylium;
import fr.welloznetwork.wellozmod.block.BlockWarpedNylium;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenBlockSplatter;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenHellGrass;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenHugeFungus;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeHellForestDecorator extends BiomeHellDecorator
{
    private WorldGenHugeFungus worldGenHugeFungus;
    private WorldGenHellGrass worldGenHellGrass;
    private WorldGenerator wartSplatterGenerator, netherrackSplatterGenerator;
    
    private BiomeHellForestDecorator(boolean crimson)
    {
        if (crimson)
        {
            this.worldGenHugeFungus = new WorldGenHugeFungus(false, 0, 0, RegistryBlocks.crimson_stem, RegistryBlocks.nether_wart);
            this.worldGenHellGrass = new WorldGenHellGrass(true);
            this.wartSplatterGenerator = new WorldGenBlockSplatter(RegistryBlocks.nether_wart, 0, RegistryBlocks.crimson_nylium, 0, 4);
            this.netherrackSplatterGenerator = new WorldGenBlockSplatter(Blocks.netherrack, 0, RegistryBlocks.crimson_nylium, 0, 4);
        }
        
        else
        {
            this.worldGenHugeFungus = new WorldGenHugeFungus(false, 0, 1, RegistryBlocks.warped_stem, RegistryBlocks.warped_wart);
            this.worldGenHellGrass = new WorldGenHellGrass(false);
            this.wartSplatterGenerator = new WorldGenBlockSplatter(RegistryBlocks.warped_wart, 1, RegistryBlocks.warped_nylium, 1, 6);
            this.netherrackSplatterGenerator = new WorldGenBlockSplatter(Blocks.netherrack, 0, RegistryBlocks.warped_nylium, 1, 6);
        }
    }
    
    public static BiomeHellForestDecorator getWarpedForestDecorator()
    {
        return new BiomeHellForestDecorator(false);
    }
    
    public static BiomeHellForestDecorator getCrimsonForestDeorator()
    {
        return new BiomeHellForestDecorator(true);
    }
    
    public void decorate(World world, Random random, int chunkX, int chunkZ)
    {
        {
            int x = chunkX + random.nextInt(16) + 8;
            int y = MathHelper.getRandomIntegerInRange(random, 32, world.provider.getActualHeight() - 8);
            int z = chunkZ + random.nextInt(16) + 8;
            
            if (this.wartSplatterGenerator != null && random.nextBoolean())
            {
                this.wartSplatterGenerator.generate(world, random, x, y, z);
            }
            
            else
            {
                this.netherrackSplatterGenerator.generate(world, random, x, y, z);
            }
        }
        
        for (int attempt = 0; attempt < 12; attempt++)
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
            
            if (world.getBlock(x, y, z) instanceof BlockCrimsonNylium || world.getBlock(x, y, z) instanceof BlockWarpedNylium && world.getBlockMetadata(x, y, z) == (this.worldGenHugeFungus.log == RegistryBlocks.crimson_stem ? 0 : 1) && world.isAirBlock(x, y + 1, z))
            {
                if (this.worldGenHugeFungus.generate(world, random, x, y + 1, z))
                {
                    this.worldGenHugeFungus.func_150524_b(world, random, x, y + 1, z);
                }
            }
        }
    }
    
    public void populate(World world, Random random, int chunkX, int chunkZ) {}
}