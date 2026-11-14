package fr.welloznetwork.wellozmod.world.gen;

import java.util.List;
import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenSoulFire;
import fr.welloznetwork.wellozmod.world.gen.structure.MapGenCloud;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class ChunkProviderTheAfter implements IChunkProvider
{
    private Random theAfterRNG;
    private World worldObj;
    private NoiseGeneratorOctaves noiseGen1, perlinNoise1;
    private double buffer[];
    private double perlinNoise[], ar[], br[];
    private MapGenCloud mapGenCloud = new MapGenCloud();
    
    public ChunkProviderTheAfter(World world, long seed)
    {
        this.worldObj = world;
        this.theAfterRNG = new Random(seed);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.theAfterRNG, 16);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.theAfterRNG, 8);
    }
    
    public boolean chunkExists(int chunkX, int chunkZ)
    {
        return true;
    }
    
    public Chunk provideChunk(int chunkX, int chunkZ)
    {
        this.theAfterRNG.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
        Block[] blocks = new Block[32768];
        
        this.setBlocksInChunk(chunkX, chunkZ, blocks);
        this.buildSurfaces(chunkX, chunkZ, blocks);
        
        this.mapGenCloud.func_151539_a(this, this.worldObj, chunkX, chunkZ, blocks);
        
        Chunk chunk = new Chunk(this.worldObj, blocks, chunkX, chunkZ);
        chunk.generateSkylightMap();
        
        return chunk;
    }
    
    public Chunk loadChunk(int chunkX, int chunkZ)
    {
        return this.provideChunk(chunkX, chunkZ);
    }
    
    public void populate(IChunkProvider iChunkProvider, int chunkX, int chunkZ)
    {
        int x = chunkX * 16, z = chunkZ * 16, var6 = this.theAfterRNG.nextInt(this.theAfterRNG.nextInt(10) + 1) + 1, var7, var8, var9, var10;
        
        BiomeGenBase biomeGenBase = this.worldObj.getBiomeGenForCoords(x + 16, z + 16);
        
        this.theAfterRNG.setSeed(this.worldObj.getSeed());
        long k = this.theAfterRNG.nextLong() / 2L * 2L + 1L;
        long l = this.theAfterRNG.nextLong() / 2L * 2L + 1L;
        this.theAfterRNG.setSeed((long) x * k + (long) z * l ^ this.worldObj.getSeed());
        
        this.mapGenCloud.generateStructuresInChunk(this.worldObj, this.theAfterRNG, chunkX, chunkZ);
        
        biomeGenBase.decorate(this.worldObj, this.theAfterRNG, x, z);
        
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomeGenBase, x + 8, z + 8, 16, 16, this.theAfterRNG);
        
        for (var7 = 0; var7 < var6; var7++)
        {
            var8 = x + this.theAfterRNG.nextInt(16) + 8;
            var9 = this.theAfterRNG.nextInt(120) + 4;
            var10 = z + this.theAfterRNG.nextInt(16) + 8;
            
            (new WorldGenSoulFire()).generate(this.worldObj, this.theAfterRNG, var8, var9, var10);
        }
    }
    
    public boolean saveChunks(boolean save, IProgressUpdate iProgressUpdate)
    {
        return true;
    }
    
    public boolean unloadQueuedChunks()
    {
        return true;
    }

    public boolean canSave()
    {
        return true;
    }

    public String makeString()
    {
        return "TheAfterRandomLevelSource";
    }

    public List getPossibleCreatures(EnumCreatureType enumCreatureType, int chunkX, int y, int chunkZ)
    {
        return this.worldObj.getBiomeGenForCoords(chunkX, chunkZ).getSpawnableList(enumCreatureType);
    }
    
    public ChunkPosition func_147416_a(World world, String name, int x, int y, int z)
    {
        return null;
    }
    
    public int getLoadedChunkCount()
    {
        return 0;
    }
    
    public void recreateStructures(int chunkX, int chunkZ) 
    {
        this.mapGenCloud.func_151539_a(this, this.worldObj, chunkX, chunkZ, (Block[]) null);
    }
    
    public void saveExtraData() {}
    
    private void setBlocksInChunk(int chunkX, int chunkZ, Block[] blocks)
    {
        this.buffer = this.initializeNoiseField(this.buffer, chunkX * 2, chunkZ * 2);
        
        for (int i1 = 0; i1 < 2; i1++)
        {
            for (int j1 = 0; j1 < 2; j1++)
            {
                for (int k1 = 0; k1 < 32; k1++)
                {
                    double d1 = this.buffer[(i1 * 3 + j1) * 33 + k1];
                    double d2 = this.buffer[(i1 * 3 + (j1 + 1)) * 33 + k1];
                    double d3 = this.buffer[((i1 + 1) * 3 + j1) * 33 + k1];
                    double d4 = this.buffer[((i1 + 1) * 3 + (j1 + 1)) * 33 + k1];
                    double d5 = (this.buffer[(i1 * 3 + j1) * 33 + (k1 + 1)] - d1) * 0.25D;
                    double d6 = (this.buffer[(i1 * 3 + (j1 + 1)) * 33 + (k1 + 1)] - d2) * 0.25D;
                    double d7 = (this.buffer[((i1 + 1) * 3 + j1) * 33 + (k1 + 1)] - d3) * 0.25D;
                    double d8 = (this.buffer[((i1 + 1) * 3 + (j1 + 1)) * 33 + (k1 + 1)] - d4) * 0.25D;
                    
                    for (int l1 = 0; l1 < 4; l1++)
                    {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.125D;
                        double d13 = (d4 - d2) * 0.125D;
                        
                        for (int i2 = 0; i2 < 8; i2++)
                        {
                            int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
                            char c = '\200';
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.125D;
                            
                            for (int k2 = 0; k2 < 8; k2++)
                            {
                                Block block = Blocks.air;
                                
                                if (d15 > 0.0D)
                                {
                                    block = Blocks.stone;
                                }
                                
                                blocks[j2] = block;
                                j2 += c;
                                d15 += d16;
                            }
                            
                            d10 += d12;
                            d11 += d13;
                        }
                        
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }
    
    private void buildSurfaces(int chunkX, int chunkZ, Block[] blocks)
    {
        for (int k = 0; k < 16; k++)
        {
            for (int l = 0; l < 16; l++)
            {
                int j1 = -1;
                int i1 = (int) (3.0D + this.theAfterRNG.nextDouble() * 0.25D);
                
                Block block1 = RegistryBlocks.warped_nylium;
                Block block2 = Blocks.dirt;
                
                for (int k1 = 127; k1 >= 0; k1--)
                {
                    int l1 = (l * 16 + k) * 128 + k1;
                    
                    Block block = blocks[l1];
                    
                    if (block == Blocks.air)
                    {
                        j1 = -1;
                    }
                    
                    else if (block == Blocks.stone)
                    {
                        if (j1 == -1)
                        {
                            if (i1 <= 0)
                            {
                                block1 = Blocks.air;
                                block2 = Blocks.stone;
                            }
                            
                            j1 = i1;
                            
                            if (k1 >= 0)
                            {
                                blocks[l1] = block1;
                            }
                            
                            else
                            {
                                blocks[l1] = block2;
                            }
                        }
                        
                        else if (j1 > 0)
                        {
                            j1--;
                            blocks[l1] = block2;
                        }
                    }
                }
            }
        }
    }
    
    private double[] initializeNoiseField(double buffer[], int chunkX, int chunkZ)
    {
        if (buffer == null)
        {
            buffer = new double[3366];
        }
        
        double d0 = 1368.824D, d1 = 684.41200000000003D;
        
        this.perlinNoise = this.perlinNoise1.generateNoiseOctaves(this.perlinNoise, chunkX, 0, chunkZ, 3, 33, 3, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.ar = this.noiseGen1.generateNoiseOctaves(this.ar, chunkX, 0, chunkZ, 3, 33, 3, d0, d1, d0);
        this.br = this.noiseGen1.generateNoiseOctaves(this.br, chunkX, 0, chunkZ, 3, 33, 3, d0, d1, d0);
        
        int id = 0;
        
        for (int j2 = 0; j2 < 3; j2++)
        {
            for (int l2 = 0; l2 < 3; l2++)
            {
                for (int j3 = 0; j3 < 33; j3++)
                {
                    double d8;
                    double d10 = this.ar[id] / 512.0D;
                    double d11 = this.br[id] / 512.0D;
                    double d12 = (this.perlinNoise[id] / 10.0D + 1.0D) / 2.0D;
                    
                    if (d12 < 0.0D)
                    {
                        d8 = d10;
                    }
                    
                    else if (d12 > 1.0D)
                    {
                        d8 = d11;
                    }
                    
                    else
                    {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    
                    d8 -= 8.0D;
                    
                    if (j3 > 33 - 32)
                    {
                        double d13 = (float) (j3 - (33 - 32)) / ((float) 32 - 1.0F);
                        d8 = d8 * (1.0D - d13) + -30.0D * d13;
                    }
                    
                    if (j3 < 8)
                    {
                        double d14 = (float) (8 - j3) / ((float) 8 - 1.0F);
                        d8 = d8 * (1.0D - d14) + -30.0D * d14;
                    }
                    
                    buffer[id] = d8;
                    id++;
                }
            }
        }
        
        return buffer;
    }
}