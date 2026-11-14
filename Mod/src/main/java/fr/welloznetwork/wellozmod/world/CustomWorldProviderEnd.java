package fr.welloznetwork.wellozmod.world;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.world.biome.CustomBiomeEndDecorator;
import fr.welloznetwork.wellozmod.world.gen.CustomChunkProviderEnd;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

@Custom(WorldProviderEnd.class)
public class CustomWorldProviderEnd extends WorldProviderEnd
{
    public void registerWorldChunkManager()
    {
        BiomeGenBase.sky.theBiomeDecorator = new CustomBiomeEndDecorator();
        
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
        this.dimensionId = 1;
        this.hasNoSky = true;
    }
    
    public IChunkProvider createChunkGenerator()
    {
        return new CustomChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }
}