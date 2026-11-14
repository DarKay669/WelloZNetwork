package fr.welloznetwork.wellozmod.world.gen.structure;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenCloud extends MapGenStructure
{
    public String func_143025_a()
    {
        return "wellozmod:cloud";
    }
    
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return this.rand.nextInt(50) == 0;
    }
    
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new Start(this.worldObj, this.rand, chunkX, chunkZ);
    }
    
    public static class Start extends StructureStart
    {
        public Start(World world, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            
            this.components.add(new StructureComponentCloud(random, (chunkX << 4) + 2, 0, (chunkZ << 4) + 2));
            this.updateBoundingBox();
        }
    }
}