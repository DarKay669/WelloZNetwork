package fr.welloznetwork.wellozmod.world.gen.structure;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class StructureComponentCloud extends CustomStructureComponent
{
    private NBTTagCompound nbtTagCompound = new NBTTagCompound();
    private int xTendency, zTendency;
    
    public StructureComponentCloud(Random random, int chunkX, int chunkY, int chunkZ)
    {
        this.coordBaseMode = 0;
        
        this.xTendency = random.nextInt(3) - 1;
        this.zTendency = random.nextInt(3) - 1;
        
        this.boundingBox = new StructureBoundingBox(chunkX, 0, chunkZ, chunkX + 100, 255, chunkZ + 100);
    }
    
    public boolean generate()
    {
        return false;
    }
    
    protected void func_143012_a(NBTTagCompound nbtTagCompound)
    {
        nbtTagCompound.setTag("cloud", this.nbtTagCompound);
    }
    
    protected void func_143011_b(NBTTagCompound nbtTagCompound)
    {
        this.nbtTagCompound = nbtTagCompound.getCompoundTag("cloud");
    }
}