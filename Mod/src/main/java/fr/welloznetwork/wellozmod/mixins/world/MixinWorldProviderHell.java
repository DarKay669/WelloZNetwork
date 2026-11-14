package fr.welloznetwork.wellozmod.mixins.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.init.RegistryBiomes;
import fr.welloznetwork.wellozmod.world.gen.CustomChunkProviderHell;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

@Mixin(WorldProviderHell.class)
public abstract class MixinWorldProviderHell extends WorldProvider
{    
    @Overwrite
    public void registerWorldChunkManager()
    {
        if (Constants.V2)
        {
            this.worldChunkMgr = new WorldChunkManagerHell(RegistryBiomes.CRIMSON_FOREST, 0.0F);
        }
        
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }
    
    @Overwrite
    public IChunkProvider createChunkGenerator()
    {
        return new CustomChunkProviderHell(this.worldObj, this.worldObj.getSeed());
    }
}