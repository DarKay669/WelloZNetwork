package fr.welloznetwork.wellozmod.mixins.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;

@Mixin(ChunkCache.class)
public abstract class MixinChunkCache implements IBlockAccess
{
    @Shadow
    private int chunkX;
    
    @Shadow
    private int chunkZ;
    
    public int getX()
    {
        return this.chunkX;
    }
    
    public int getY()
    {
        return 0;
    }
    
    public int getZ()
    {
        return this.chunkZ;
    }
}