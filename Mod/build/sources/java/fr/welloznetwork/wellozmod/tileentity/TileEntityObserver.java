package fr.welloznetwork.wellozmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityObserver extends TileEntity
{
    private int targetId, targetMetadata;
    
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.targetId = nbtTagCompound.getInteger("targetId");
        this.targetMetadata = nbtTagCompound.getInteger("targetMetadata");
    }
    
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("targetId", this.targetId);
        nbtTagCompound.setInteger("targetMetadata", this.targetMetadata);
    }
    
    public void setValues(int targetId, int targetMetadata)
    {
        this.targetId = targetId;
        this.targetMetadata = targetMetadata;
        this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.blockType);
    }
    
    public int getTargetId()
    {
        return this.targetId;
    }
    
    public int getTargetMetadata()
    {
        return this.targetMetadata;
    }
}