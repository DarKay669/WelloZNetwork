package fr.welloznetwork.wellozmod.world.biome;

import java.util.Random;

import net.minecraft.world.World;

public abstract class BiomeHellDecorator
{
    public abstract void populate(World world, Random random, int chunkX, int chunkZ);
    
    public abstract void decorate(World world, Random random, int chunkX, int chunkZ);
}