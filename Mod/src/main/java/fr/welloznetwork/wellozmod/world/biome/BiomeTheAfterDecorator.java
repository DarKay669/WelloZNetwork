package fr.welloznetwork.wellozmod.world.biome;

import java.util.Calendar;
import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.CustomWorldGenMinable;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenChristmasTree;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenCloud;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenFloatingIsland;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenFoliage;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeTheAfterDecorator extends BiomeDecorator
{
    private World world;
    private Random random;
    private BiomeGenBase biomeGenBase;
    private WorldGenDoublePlant worldGenDoublePlant = new WorldGenDoublePlant();
    private WorldGenFloatingIsland worldGenFloatingIsland = new WorldGenFloatingIsland();
    private WorldGenChristmasTree worldGenChristmasTree = new WorldGenChristmasTree();
    private WorldGenFoliage worldGenFoliage = new WorldGenFoliage();
    private CustomWorldGenMinable customWorldGenMinable = new CustomWorldGenMinable();
    private WorldGenCloud worldGenCloud = new WorldGenCloud();
    
    public BiomeTheAfterDecorator()
    {
        super();
    }
    
    public void decorateChunk(World world, Random random, BiomeGenBase biomeGenBase, int chunkX, int chunkZ)
    {
        this.world = world;
        this.random = random;
        this.chunk_X = chunkX;
        this.chunk_Z = chunkZ;
        this.biomeGenBase = biomeGenBase;
        this.genDecorations(biomeGenBase);
        this.world = null;
        this.random = null;
    }
    
    protected void genDecorations(BiomeGenBase biomeGenBase)
    {
        this.generateClouds(4, 50, this.nextInt(64) + 96);
        this.generateClouds(8, 26, this.nextInt(64) + 32);
        this.generateClouds(16, 14, this.nextInt(64) + 64);
        
        if (this.shouldSpawn(37))
        {
            this.worldGenFloatingIsland.generate(this.world, this.random, this.chunk_X + 8, this.nextInt(64) + 32, this.chunk_Z + 8);
        }
        
        if (this.shouldSpawn(3))
        {
            this.generateOre(Blocks.dirt, 32, 20, 128);
        }
        
        this.generateFoliage(RegistryBlocks.wither_rose);
        
        this.generateOre(RegistryBlocks.ruby_ore, 6, 6, 64);
        this.generateOre(RegistryBlocks.zarux_ore, 3, 4, 32);
        
        if (Calendar.getInstance().get(Calendar.MONTH) + 1 == 12)
        {
            if (this.shouldSpawn(15))
            {
                int x = this.chunk_X + 8;
                int z = this.chunk_Z + 8;
                int y = this.world.getHeightValue(x, z);
                
                this.worldGenChristmasTree.generate(this.world, this.random, x, y, z);
            }
            
            this.generateFoliage(RegistryBlocks.winter_rose);
        }
        
        for (int i = 0; i < 10; i++)
        {
            int x = this.chunk_X + this.random.nextInt(16) + 8;
            int y = this.world.getHeight() * 2;
            int z = this.chunk_Z + this.random.nextInt(16) + 8;
            
            if (y > 0)
            {
                int l = this.random.nextInt(y);
                
                this.biomeGenBase.getRandomWorldGenForGrass(this.random).generate(this.world, this.random, x, l, z);
            }
        }
        
        if (TerrainGen.decorate(this.world, this.random, this.chunk_X, this.chunk_Z, EventType.GRASS))
        {
            for (int i = 0; i < 7; i++)
            {
                int x = this.chunk_X + this.random.nextInt(16) + 8;
                int y = this.random.nextInt(this.world.getHeight() + 32);
                int z = this.chunk_Z + this.random.nextInt(16) + 8;
                
                this.worldGenDoublePlant.func_150548_a(2);
                this.worldGenDoublePlant.generate(this.world, this.random, x, y, z);
            }
        }
    }
    
    private int nextInt(int max)
    {
        return this.random.nextInt(max);
    }
    
    private boolean shouldSpawn(int chance)
    {
        return this.nextInt(chance) == 0;
    }
    
    private void generateFoliage(Block block)
    {
        this.worldGenFoliage.setPlantBlock(block);
        
        for (int i = 0; i < 2; i++)
        {
            this.worldGenFoliage.generate(this.world, this.random, this.chunk_X + this.nextInt(16) + 8, this.nextInt(128), this.chunk_Z + this.nextInt(16) + 8);
        }
    }
    
    private void generateOre(Block block, int size, int chance, int y)
    {
        this.customWorldGenMinable.setSize(size);
        this.customWorldGenMinable.setBlock(block);
        
        for (int chances = 0; chances < chance; chances++)
        {
            this.customWorldGenMinable.generate(this.world, this.random, this.chunk_X + this.nextInt(16), this.nextInt(y), this.chunk_Z + this.nextInt(16));
        }
    }
    
    private void generateClouds(int amount, int chance, int y)
    {
        if (this.shouldSpawn(chance))
        {
            this.worldGenCloud.setCloudAmount(amount);
            this.worldGenCloud.generate(this.world, this.random, this.chunk_X + this.nextInt(16), this.nextInt(y), this.chunk_Z + this.nextInt(16));
        }
    }
}