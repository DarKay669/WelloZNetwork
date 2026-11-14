package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenBulbTree;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenBambooForest extends BiomeGenBase
{    
    public BiomeGenBambooForest()
    {
        super(53);
        setBiomeName("Bamboo Forest");
        setColor(10739795);
        setHeight(new Height(0.1F, 0.3F));
        setTemperatureRainfall(1.2F, 0.9F);
        this.theBiomeDecorator.treesPerChunk = 30;
        this.theBiomeDecorator.grassPerChunk = 5;
    }
    
    public void decorate(World world, Random random, int chunkX, int chunkZ) 
    {
        super.decorate(world, random, chunkX, chunkZ);
        int var5 = 12 + random.nextInt(6);
        
        for (int var6 = 0; var6 < var5; var6++)
        {
            int x = chunkX + random.nextInt(16), y = random.nextInt(28) + 4, z = chunkZ + random.nextInt(16);
            
            Block block = world.getBlock(x, y, z);
            
            if (block != null && block.isReplaceableOreGen(world, x, y, z, Blocks.stone))
            {
                world.setBlock(x, y, z, RegistryBlocks.random_ore, 2, 2);
            }
        }
    }
    
    public void genTerrainBlocks(World world, Random random, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
    {
        this.topBlock = Blocks.dirt;
        this.field_150604_aj = 2;
        
        this.genBiomeTerrain(world, random, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    } 
    
    public WorldGenAbstractTree func_150567_a(Random random) 
    {
        return random.nextInt(3) == 0 ? new WorldGenShrub(0, 0) : new WorldGenBulbTree(RegistryBlocks.bamboo, RegistryBlocks.bamboo_leaves, 0, 1, false, 10, 12, false);
    }
    
    public WorldGenerator getRandomWorldGenForGrass(Random random)
    {
        return random.nextInt(4) == 0 ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 2);
    }
    
    public int getBiomeGrassColor(int x, int y, int z)
    {
        return plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D) < -0.7D ? 13949781 : (plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D) < -0.3 ? 12311892 : 10739795);
    }
    
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        return plantNoise.func_151601_a((double) x * 0.0225D, (double) z * 0.0225D) < - 0.7D ? 13949781 : (plantNoise.func_151601_a((double) x * 0.0225D, (double) z * 0.0225D) < -0.3 ? 12311892 : 10739795);
    }
}