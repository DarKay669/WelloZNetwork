package fr.welloznetwork.wellozmod.mixins.world;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Block;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.WorldServer;

@Mixin(WorldServer.class)
public class MixinWorldServer implements IObserverWorldExtension
{
    @Shadow
    private Set<NextTickListEntry> pendingTickListEntriesHashSet;
    
    public boolean hasScheduledUpdate(int x, int y, int z, Block block)
    {
        return this.pendingTickListEntriesHashSet.contains(new NextTickListEntry(x, y, z, block));
    }
}