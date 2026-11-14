package fr.welloznetwork.wellozmod.world;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.IWorldGenerator;
import fr.welloznetwork.wellozmod.block.BlockChorusFlower;
import fr.welloznetwork.wellozmod.common.CustomChestGenHooks;
import fr.welloznetwork.wellozmod.init.RegistryBiomes;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.CustomWorldGenPumpkin;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenCoral;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenQuickSand;
import fr.welloznetwork.wellozmod.world.gen.structure.StructureCandyCane;
import fr.welloznetwork.wellozmod.world.gen.structure.StructureOceanMonument;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class WorldGenerator implements IWorldGenerator
{    
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider iChunkProvider)
	{
	    if (world.provider instanceof WorldProviderHell)
	    {
	        this.genNether(world, chunkX * 16, chunkZ * 16, random);
	    }
	    
	    else if (world.provider instanceof WorldProviderSurface)
	    {
	        this.genOverworld(world, chunkX * 16, chunkZ * 16, random);
	    }
	    
	    else if (world.provider instanceof WorldProviderEnd)
	    {
	        this.genEnd(world, chunkX * 16, chunkZ * 16, random);
	    }
	}
	
	private void genNether(World world, int chunkX, int chunkZ, Random random)
    {
	    new WorldGenMinable(RegistryBlocks.basalt, 0, 7, Blocks.netherrack).generate(world, random, chunkX + random.nextInt(16), random.nextInt(16), chunkZ + random.nextInt(16));
        new WorldGenMinable(RegistryBlocks.black_stone, 0, 7, Blocks.netherrack).generate(world, random, chunkX + random.nextInt(16), random.nextInt(16), chunkZ + random.nextInt(16));
        
	    for (int i = 0; i < 1; i++)
	    {
	        int posX = chunkX + random.nextInt(16);
	        int posY = 5 + random.nextInt(90);
	        int posZ = chunkZ + random.nextInt(16);
	        (new WorldGenMinable(RegistryBlocks.ancient_debris, 0, 4, Blocks.netherrack)).generate(world, random, posX, posY, posZ);
	    }
	    
	    for (int i = 0; i < 1; i++)
	    {
	        int posX = chunkX + random.nextInt(16) + 8;
	        int posY = random.nextInt(128);
	        int posZ = chunkZ + random.nextInt(16) + 8;
	        (new WorldGenFlowers(RegistryBlocks.wither_rose)).generate(world, random, posX, posY, posZ);
	    }
	    
	    CustomChestGenHooks.getChestGenHooksNether();
    }
	
	private void genOverworld(World world, int chunkX, int chunkZ, Random random)
	{
	    BiomeGenBase biomeGenBase = world.getBiomeGenForCoords(chunkX * 16 + 16, chunkZ * 16 + 16);
	    	    	    
	    new WorldGenMinable(RegistryBlocks.zarkonium_ore, 0, 3, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(12), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.random_ore, 0, 3, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(17), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.xp_ore, 0, 3, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(19), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.deepslate_coal_ore, 0, 15, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.deepslate_copper_ore, 0, 6, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(48), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.deepslate_diamond_ore, 0, 10, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(1), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.deepslate_emerald_ore, 0, 3, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(236), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.deepslate_gold_ore, 0, 4, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.deepslate_iron_ore, 0, 5, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(15), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.deepslate_lapis_ore, 0, 8, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(1), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.deepslate_redstone_ore, 0, 8, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(1), chunkZ + random.nextInt(16));
	    new WorldGenMinable(RegistryBlocks.cobbled_deepslate, 0, 3, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
        new WorldGenMinable(RegistryBlocks.deepslate, 0, 3, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
        new WorldGenMinable(RegistryBlocks.infested_deepslate, 0, 3, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
        new WorldGenMinable(RegistryBlocks.tuff, 0, 6, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
        new WorldGenMinable(RegistryBlocks.stone_andesite, 0, 15, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
        new WorldGenMinable(RegistryBlocks.stone_diorite, 0, 15, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
        new WorldGenMinable(RegistryBlocks.stone_granite, 0, 15, Blocks.stone).generate(world, random, chunkX + random.nextInt(16), random.nextInt(32), chunkZ + random.nextInt(16));
	    
	    if (biomeGenBase == BiomeGenBase.desert || biomeGenBase == RegistryBiomes.DESERT_MOUNTAIN)
	    {
	        for (int i = 0; i < 100; i++)
	        {
	            int posX = chunkX + random.nextInt(16) + 2;
	            int posY = random.nextInt(256);
	            int posZ = random.nextInt(16) + 2;
	            (new WorldGenQuickSand(RegistryBlocks.quick_sand, 7)).generate(world, random, posX, posY, posZ);
	        }
	    }
	    
	    if (biomeGenBase == RegistryBiomes.WINTER)
	    {
	        for (int i = 0; i < 1; i++)
            {
                int posX = chunkX + random.nextInt(16) + 8;
                int posZ = chunkZ + random.nextInt(16) + 8;
                int posY = random.nextInt(world.getHeightValue(posX, posZ) * 2);
                (new WorldGenFlowers(RegistryBlocks.winter_rose)).generate(world, random, posX, posY, posZ);
            }
	        
	        for (int i = 0; i < 2; i++)
	        {
	            int posX = chunkX + random.nextInt(16);
	            int posZ = chunkZ + random.nextInt(16);
	            int posY = world.getHeightValue(posX, posZ);
	            (new StructureCandyCane()).generate(world, random, posX, posY, posZ);
	        }
	    }
	    
	    if (biomeGenBase == RegistryBiomes.HAUNTED || biomeGenBase == RegistryBiomes.MANGROVE_SWAMP)
	    {
	        for (int i = 0; i < 2; i++)
	        {
	            int posX = chunkX + random.nextInt(16) + 2;
	            int posY = random.nextInt(256);
	            int posZ = chunkZ + random.nextInt(16) + 2;
	            (new WorldGenLakes(RegistryBlocks.poison_water)).generate(world, random, posX, posY, posZ);
	        }
	    }
	    
	    if (biomeGenBase == RegistryBiomes.EXTREME_JUNGLE || biomeGenBase == RegistryBiomes.MEADOW || biomeGenBase == RegistryBiomes.SHRUBLAND)
	    {
    	    for (int i = 0; i < 2; i++)
    	    {
    	        int posX = chunkX + random.nextInt(16) + 3;
    	        int posZ = chunkZ + random.nextInt(16) + 3;
    	        int posY = random.nextInt(world.getHeightValue(posX, posZ) * 2);
    	        (new CustomWorldGenPumpkin()).generate(world, random, posX, posY, posZ);
    	    }
    	}
	    
	    if (biomeGenBase == BiomeGenBase.deepOcean || biomeGenBase == BiomeGenBase.ocean)
	    {
    	    for (int i = 0; i < 2; i++)
    	    {
    	        int posX = chunkX + random.nextInt(16);
    	        int posY = 45 + random.nextInt(10);
    	        int posZ = chunkZ + random.nextInt(16);
    	        
    	        if (random.nextInt(25) == 0)
    	        {
    	            (new WorldGenCoral()).generate(world, random, posX, posY, posZ);
    	        }
    	    }
    	}
	    
	    for (int x = chunkX * 16; x < chunkX * 16 + 16; x++)
	    {
	        for (int z = chunkZ * 16; z < chunkZ * 16 + 16; z++)
	        {
	            for (int y = 0; y < world.getActualHeight(); y++)
	            {
	                if (world.getBlock(x, y, z) == Blocks.dirt && world.getBlockMetadata(x, y, z) == 1)
	                {
	                    world.setBlock(x, y, z, RegistryBlocks.coarse_dirt, 0, 2);
	                }
	            }
	        }
	    }
	    
	    for (BiomeGenBase biomeGenBase1 : BiomeDictionary.getBiomesForType(Type.CONIFEROUS))
	    {
	        biomeGenBase1.addFlower(RegistryBlocks.sweet_berry_bush, 3, 1);
	    }
	    
	    if (ArrayUtils.contains(BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(chunkX << 4, chunkZ << 4)), Type.MESA))
	    {
	        new WorldGenMinable(Blocks.gold_ore, 8).generate(world, random, chunkX + random.nextInt(20), 32, chunkZ + random.nextInt(80));
	    }
	    
	    IChunkProvider iChunkProvider = world.getChunkProvider() instanceof ChunkProviderServer ? ((ChunkProviderServer) world.getChunkProvider()).currentChunkProvider : world.getChunkProvider();
	    
	    if (!(iChunkProvider instanceof ChunkProviderFlat) || world.getWorldInfo().getGeneratorOptions().contains("decoration"))
	    {
	        if (StructureOceanMonument.canSpawnAt(world, chunkX, chunkZ))
	        {
	            int x = (chunkX << 4) + random.nextInt(16) + 8;
	            int z = (chunkZ << 4) + random.nextInt(16) + 8;
	            
	            int y;
	            
	            for (y = world.getActualHeight(); y > 0; y--)
	            {
	                if (!world.isAirBlock(x, y, z))
	                {
	                    break;
	                }
	                
	                int monumentCeiling = y + (1 + random.nextInt(3));
	                StructureOceanMonument.buildTemple(world, x, monumentCeiling - 22, z);
	                return;
	            }
	        }
	    }
	    
	    CustomChestGenHooks.getChestGenHooksOverworld();
	}
	
    private void genEnd(World world, int chunkX, int chunkZ, Random random) 
    {
        if (!(world.provider instanceof CustomWorldProviderEnd) && world.provider instanceof WorldProviderEnd)
        {
            for (int i = 0; i < 1; i++)
            {
                int posX = chunkX + random.nextInt(16);
                int posY = 256;
                int posZ = chunkZ + random.nextInt(16);
                
                for (; posY > 0; posY--)
                {
                    if (!world.getBlock(posX, posY, posZ).isAir(world, posX, posY, posZ))
                    {
                        break;
                    }
                }
                
                if (posY > 0 && BlockChorusFlower.canPlantStay(world, posX, posY + 1, posZ))
                {
                    BlockChorusFlower.generateChorusPlant(world, posX, posY + 1, posZ, 0);
                }
            }
        }
    }
}