package fr.welloznetwork.wellozmod.mixins.world;

import net.minecraft.block.Block;

public interface IObserverWorldExtension
{
    boolean hasScheduledUpdate(int x, int y, int z, Block block);
}