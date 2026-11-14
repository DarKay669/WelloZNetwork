package fr.welloznetwork.wellozmod.block;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface IBlockObserver
{
    default void observedNeighborChange(World world, int x, int y, int z, Block block, int x1, int y1, int z1) {}
}