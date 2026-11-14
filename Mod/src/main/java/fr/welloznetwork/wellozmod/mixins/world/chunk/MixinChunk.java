package fr.welloznetwork.wellozmod.mixins.world.chunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.block.BlockObserver;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

@Mixin(Chunk.class)
public class MixinChunk
{
    @Inject(method = "populateChunk", at = @At(value = "HEAD"))
    private void disableObserverNotifications(IChunkProvider iChunkProvider, IChunkProvider iChunkProvider1, int x, int z, CallbackInfo callbackInfo)
    {
        BlockObserver.disableNotifications();
    }
    
    @Inject(method = "populateChunk", at = @At(value = "RETURN"))
    private void enableObserverNotifications(IChunkProvider iChunkProvider, IChunkProvider iChunkProvider1, int x, int z, CallbackInfo callbackInfo)
    {
        BlockObserver.enableNotifications();
    }
}