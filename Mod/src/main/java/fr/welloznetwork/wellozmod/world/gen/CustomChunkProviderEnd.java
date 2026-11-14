package fr.welloznetwork.wellozmod.world.gen;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event.Result;
import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.block.BlockChorusFlower;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenEndIsland;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent.ReplaceBiomeBlocks;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Post;

@Custom(ChunkProviderEnd.class)
public class CustomChunkProviderEnd implements IChunkProvider
{
    private Random random;
    private NoiseGeneratorOctaves lperlinNoise1, lperlinNoise2, perlinNoise1, noiseGen5, noiseGen6;
    private World worldObj;
    private boolean mapFeaturesEnabled;
    private NoiseGeneratorSimplex islandNoise;
    private double[] buffer, pnr, ar, br;
    private BiomeGenBase[] biomesForGeneration;
    private WorldGenEndIsland worldGenEndIsland = new WorldGenEndIsland();
    
    public CustomChunkProviderEnd(World world, long seed)
    {
        this.worldObj = world;
        this.mapFeaturesEnabled = true;
        this.random = new Random(seed);
        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.random, 16);
        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.random, 16);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.random, 8);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.random, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.random, 16);
        this.islandNoise = new NoiseGeneratorSimplex(this.random);
    }
    
    public boolean chunkExists(int chunkX, int chunkZ)
    {
        return true;
    }
    
    public Chunk provideChunk(int chunkX, int chunkZ)
    {
        Block[] blocks = new Block[32768];
        byte[] metadata = new byte[blocks.length];
        
        this.random.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
        this.setBlocksInChunk(chunkX, chunkZ, blocks);
        
        Chunk chunk = new Chunk(this.worldObj, blocks, metadata, chunkX, chunkZ);
        byte[] bytes = chunk.getBiomeArray();
        
        for (int i = 0; i < bytes.length; i++)
        {
            bytes[i] = (byte) this.biomesForGeneration[i].biomeID;
        }
        
        chunk.generateSkylightMap();
        
        return chunk;
    }
    
    public Chunk loadChunk(int chunkX, int chunkZ)
    {
        return this.provideChunk(chunkX, chunkZ);
    }
    
    public void populate(IChunkProvider iChunkProvider, int chunkX, int chunkZ) 
    {
        BlockFalling.fallInstantly = true;
        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent(iChunkProvider, this.worldObj, this.random, chunkX, chunkZ, false));
        
        int xPos = chunkX * 16;
        int yPos = 0;
        int zPos = chunkZ * 16;
        
        this.worldObj.getBiomeGenForCoords(xPos + 16, zPos + 16).decorate(this.worldObj, this.random, xPos, zPos);
        
        long i = (long) chunkX * (long) chunkX + (long) chunkZ * (long) chunkZ;
        
        if (i > 4096L)
        {
            float f = this.getIslandHeightValue(chunkX, chunkZ, 1, 1);
            
            if (f < -20.0F && this.random.nextInt(14) == 0)
            {
                this.worldGenEndIsland.generate(this.worldObj, this.random, xPos + (this.random.nextInt(16) + 8), yPos + (55 + this.random.nextInt(16)), zPos + (this.random.nextInt(16) + 8));
                
                if (this.random.nextInt(4) == 0)
                {
                    this.worldGenEndIsland.generate(this.worldObj, this.random, xPos + (this.random.nextInt(16) + 8), yPos + (55 + this.random.nextInt(16)), zPos + (this.random.nextInt(16) + 8));
                }
            }
            
            if (this.getIslandHeightValue(chunkX, chunkZ, 1, 1) > 40.0F)
            {
                int j = this.random.nextInt(5);
                
                for (int k = 0; k < j; k++)
                {
                    int l = this.random.nextInt(16) + 8;
                    int i1 = this.random.nextInt(16) + 8;
                    int j1 = this.worldObj.getTopSolidOrLiquidBlock(xPos + l, zPos + i1);
                    
                    if (j1 > 0)
                    {
                        int k1 = j1;
                        
                        if (this.worldObj.isAirBlock(xPos + l, yPos + k1, zPos + i1) && this.worldObj.getBlock(xPos + l, yPos + k1 - 1, zPos + i1) == Blocks.end_stone)
                        {
                            BlockChorusFlower.generateChorusPlant(this.worldObj, xPos + l, yPos + k1, zPos + i1, 8);
                        }
                    }
                }
            }
        }
        
        MinecraftForge.EVENT_BUS.post(new Post(iChunkProvider, this.worldObj, this.random, chunkX, chunkZ, false));
        BlockFalling.fallInstantly = false;
    }
    
    public boolean saveChunks(boolean save, IProgressUpdate iProgressUpdate)
    {
        return true;
    }
    
    public boolean unloadQueuedChunks()
    {
        return false;
    }
    
    public boolean canSave()
    {
        return true;
    }
    
    public String makeString()
    {
        return "RandomLevelSource";
    }
    
    public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, int chunkX, int p_73155_3_, int chunkZ)
    {
        return this.worldObj.getBiomeGenForCoords(chunkX, chunkZ).getSpawnableList(enumCreatureType);
    }
    
    public ChunkPosition func_147416_a(World world, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
    {
        return null;
    }
    
    public int getLoadedChunkCount()
    {
        return 0;
    }
    
    public void recreateStructures(int chunkX, int chunkZ) {}
    
    public void saveExtraData() {}
    
    private void setBlocksInChunk(int chunkX, int chunkZ, Block[] blocks)
    {
        int i = 2, j = i + 1, k = 33, l = i + 1;
        this.buffer = this.getHeights(this.buffer, chunkX * i, 0, chunkZ * i, j, k, l);
        
        for (int i1 = 0; i1 < i; i1++)
        {
            for (int j1 = 0; j1 < i; j1++)
            {
                for (int k1 = 0; k1 < 32; k1++)
                {
                    double d0 = 0.25D;
                    double d1 = this.buffer[((i1) * l + j1) * k + k1];
                    double d2 = this.buffer[((i1) * l + j1 + 1) * k + k1];
                    double d3 = this.buffer[((i1 + 1) * l + j1) * k + k1];
                    double d4 = this.buffer[((i1 + 1) * l + j1 + 1) * k + k1];
                    double d5 = (this.buffer[((i1) * l + j1) * k + k1 + 1] - d1) * d0;
                    double d6 = (this.buffer[((i1) * l + j1 + 1) * k + k1 + 1] - d2) * d0;
                    double d7 = (this.buffer[((i1 + 1) * l + j1) * k + k1 + 1] - d3) * d0;
                    double d8 = (this.buffer[((i1 + 1) * l + j1 + 1) * k + k1 + 1] - d4) * d0;
                    
                    for (int l1 = 0; l1 < 4; l1++)
                    {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        
                        for (int i2 = 0; i2 < 8; i2++)
                        {
                            int j3 = i2 + i1 * 8 << 11 | j1 * 8 << 7 | k1 * 4 + l1;
                            
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            
                            for (int j2 = 0; j2 < 8; j2++)
                            {
                                Block iBlockState = Blocks.air;
                                
                                if (d15 > 0.0D)
                                {
                                    iBlockState = Blocks.end_stone;
                                }
                                
                                blocks[j3] = iBlockState;
                                j3 += 128;
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
    
    private void buildSurfaces(int chunkX, int chunkZ, Block[] blocks, BiomeGenBase[] biomeGenBases, byte[] metadatas)
    {
        ReplaceBiomeBlocks event = new ReplaceBiomeBlocks(this, chunkX, chunkZ, blocks, metadatas, biomeGenBases, this.worldObj);
        MinecraftForge.EVENT_BUS.post(event);
        
        if (event.getResult() == Result.DENY)
        {
            return;
        }
        
        for (int i = 0; i < 16; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                int k = 1, l = -1;
                
                Block iBlockState = Blocks.end_stone, iBlockState1 = Blocks.end_stone;
                
                for (int i1 = 127; i1 >= 0; i1--)
                {
                    int k1 = (j * 16 + i) * 128 + i1;
                    
                    Block iBlockState2 = blocks[k1];
                    
                    if (iBlockState2 == null || iBlockState2.getMaterial() == Material.air)
                    {
                        l = -1;
                    }
                    
                    else if (iBlockState2 == Blocks.stone)
                    {
                        if (l == -1)
                        {
                            if (k <= 0)
                            {
                                iBlockState = Blocks.air;
                                iBlockState1 = Blocks.end_stone;
                            }
                            
                            l = k;
                            
                            if (i1 >= 0)
                            {
                                blocks[k1] = iBlockState;
                            }
                            
                            else
                            {
                                blocks[k1] = iBlockState1;
                            }
                        }
                        
                        else if (l > 0)
                        {
                            l--;
                            blocks[k1] = iBlockState1;
                        }
                    }
                }
            }
        }
    }
    
    private float getIslandHeightValue(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_)
    {
        float f = (float) (p_185960_1_ * 2 * + p_185960_3_);
        float f1 = (float) (p_185960_2_ * 2 * p_185960_4_);
        float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;
        
        if (f2 > 80.0F)
        {
            f2 = 80.0F;
        }
        
        if (f2 < -100.0F)
        {
            f2 = -100.0F;
        }
        
        for (int i = -12; i <= 12; i++)
        {
            for (int j = -12; j <= 12; j++)
            {
                long k = p_185960_1_ + i;
                long l = p_185960_2_ + j;
                
                if (k * k + l * l > 4096L && this.islandNoise.func_151605_a((double) k, (double) l) < -0.8999999761581421D)
                {
                    float f3 = (MathHelper.abs((float) k) * 3439.0F + MathHelper.abs((float) l) * 147.0F) % 13.0F + 9.0F;
                    f = (float) (p_185960_3_ - i * 2);
                    f1 = (float) (p_185960_4_ -j * 2);
                    float f4 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * f3;
                    
                    if (f4 > 80.0F)
                    {
                        f4 = 80.0F;
                    }
                    
                    if (f4 < -100.0F)
                    {
                        f4 = -100.0F;
                    }
                    
                    if (f4 > f2)
                    {
                        f2 = f4;
                    }
                }
            }
        }
        
        return f2;
    }
    
    private boolean isIslandChunk(int chunkX, int chunkZ)
    {
        return (long) chunkX * (long) chunkX + (long) chunkZ * (long) chunkZ > 4096L && this.getIslandHeightValue(chunkX, chunkZ, 1, 1) >= 0.0F;
    }
    
    private double[] getHeights(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_, int p_185963_5_, int p_185963_6_, int p_185963_7_)
    {
        if (p_185963_1_ == null)
        {
            p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
        }
        
        double d0 = 684.412D, d1 = 684.412D;
        d0 = d0 * 2.0D;
        
        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, d1, d0);
        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, d1, d0);
        
        int i = p_185963_2_ / 2, j = p_185963_4_ / 2, k = 0;
        
        for (int l = 0; l < p_185963_5_; l++)
        {
            for (int i1 = 0; i1 < p_185963_7_; i1++)
            {
                float f = this.getIslandHeightValue(i, j, l, i1);
                
                for (int j1 = 0; j1 < p_185963_6_; j1++)
                {
                    double d2 = 0.0D;
                    double d3 = this.ar[k] / 512.0D;
                    double d4 = this.br[k] / 512.0D;
                    double d5 = (this.pnr[k] / 10.0D + 1.0D) / 2.0D;
                    
                    if (d5 < 0.0D)
                    {
                        d2 = d3;
                    }
                    
                    else if (d5 > 1.0D)
                    {
                        d2 = d4;
                    }
                    
                    else
                    {
                        d2 = d3 + (d4 - d3) * d5;
                    }
                    
                    d2 = d2 - 8.0D;
                    d2 = d2 + (double) f;
                    int k1 = 2;
                    
                    if (j1 > p_185963_6_ / 2 - k1)
                    {
                        double d6 = (float) (j1 - (p_185963_6_ / 2 - k1)) / 64.0F;
                        d6 = MathHelper.clamp_double(d6, 0.0D, 1.0D);
                        d2 = d2 * (1.0D - d6) + -3000.0D * d6;
                    }
                    
                    k1 = 8;
                    
                    if (j1 < k1)
                    {
                        double d7 = (float) (k1 -j1) / ((float) k1 - 1.0F);
                        d2 = d2 * (1.0D - d7) + -30.0D * d7;
                    }
                    
                    p_185963_1_[k] = d2;
                    k++;
                }
            }
        }
        
        return p_185963_1_;
    }
}