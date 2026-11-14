package fr.welloznetwork.wellozmod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLights extends TileEntity
{
    public int leaf, type;
    
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("leaf", this.leaf);
        nbtTagCompound.setInteger("type", this.type);
    }
    
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.leaf = nbtTagCompound.getInteger("leaf");
        this.type = nbtTagCompound.getInteger("type");
    }
    
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }
    
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet)
    {
        NBTTagCompound nbtTagCompound = packet.func_148857_g();
        readFromNBT(nbtTagCompound);
    }
}