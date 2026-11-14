package fr.welloznetwork.wellozmod.mixins.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import fr.welloznetwork.wellozmod.world.biome.CustomBiomeEndDecorator;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

@Mixin(WorldProviderEnd.class)
public abstract class MixinWorldProviderEnd extends WorldProvider
{
    @Overwrite
    public void registerWorldChunkManager()
    {
        BiomeGenBase.sky.theBiomeDecorator = new CustomBiomeEndDecorator();
        
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = 1;
    }
    
    @Overwrite
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }
}