package fr.welloznetwork.wellozmod.world.gen.structure;

import java.util.Random;

import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

@Custom(StructureComponent.class)
public abstract class CustomStructureComponent extends StructureComponent
{
    private Random random;
    private World worldObj;
    private StructureBoundingBox structureBoundingBox;
    
    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox)
    {
        this.worldObj = world;
        this.random = random;
        this.structureBoundingBox = structureBoundingBox;
        
        return this.generate();
    }
    
    public abstract boolean generate();
    
    protected void func_143012_a(NBTTagCompound nbtTagCompound) {}
    
    protected void func_143011_b(NBTTagCompound nbtTagCompound) {}
}