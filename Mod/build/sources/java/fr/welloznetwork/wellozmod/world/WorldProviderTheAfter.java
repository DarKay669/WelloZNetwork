package fr.welloznetwork.wellozmod.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.world.biome.WorldChunkManagerTheAfter;
import fr.welloznetwork.wellozmod.world.gen.ChunkProviderTheAfter;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderTheAfter extends WorldProvider
{
    private float[] colorsSunriseSunset = new float[4];
    
    public WorldProviderTheAfter()
    {
        super();
    }
    
    protected void generateLightBrightnessTable()
    {
        for (int i = 0; i <= 15; i++)
        {
            float f = 1.0F - (float) i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f) / (f * 3.0F + 1.0F) * (1.0F - 0.2F) + f;
        }
    }
    
    protected void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManagerTheAfter();
    }
    
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderTheAfter(this.worldObj, this.worldObj.getSeed());
    }
    
    public boolean canCoordinateBeSpawn(int chunkX, int chunkZ)
    {
        return false;
    }
    
    public boolean isSurfaceWorld()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float f, float f1)
    {
        float f2 = 0.4F;
        float f3 = MathHelper.cos(f * 3.141593F * 2.0F) - 0.0F;
        float f4 = -0.0F;
        
        if (f3 >= f4 - f2 && f3 <= f4 + f2)
        {
            float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
            
            f6 *= f6;
            
            this.colorsSunriseSunset[0] = f5 * 0.3F + 0.1F;
            this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = f5 * f5 * 0.7F + 0.2F;
            this.colorsSunriseSunset[3] = f6;
            
            return this.colorsSunriseSunset;
        }
        
        else
        {
            return null;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float f, float f1)
    {
        int i = 0x9393BC;
        float f2 = MathHelper.cos(f * 3.141593F * 2.0F) * 2.0F + 0.5F;
        
        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        
        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }
        
        float f3 = (i >> 16 & 0xFF) / 255.0F;
        float f4 = (i >> 8 & 0xFF) / 255.0F;
        float f5 = (i & 0xFF) / 255.0F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.94F + 0.06F;
        
        return Vec3.createVectorHelper(f3, f4, f5);
    }
    
    public boolean canRespawnHere()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return -5.0F;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor()
    {
        return 100.0D;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int chunkX, int chunkZ)
    {
        return false;
    }
    
    public String getDimensionName()
    {
        return "The After";
    }
    
    public String getSaveFolder()
    {
        return "DIM-2";
    }
    
    public double getHorizon()
    {
        return 0.0D;
    }
    
    public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }
    
    public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }
}