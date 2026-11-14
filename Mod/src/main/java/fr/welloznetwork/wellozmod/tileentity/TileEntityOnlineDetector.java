package fr.welloznetwork.wellozmod.tileentity;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityOnlineDetector extends TileEntity
{
    private String playerName = "";
    private boolean isPlayerOnline = false;
    
    public String getPlayerName()
    {
        return this.playerName;
    }
    
    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
        markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
    
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }
    
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.func_148857_g());
    }
    
    public void updateEntity()
    {
        if (this.worldObj.getTotalWorldTime() % 20L == 0L)
        {
            boolean isPlayerOnline = this.isPlayerOnline(this.playerName);
            
            if (isPlayerOnline != this.isPlayerOnline)
            {
                int metadata;
                markDirty();
                
                if (isPlayerOnline)
                {
                    metadata = 1;
                }
                
                else
                {
                    metadata = 0;
                }
                
                this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, metadata, 3);
                this.isPlayerOnline = isPlayerOnline;
            }
        }
        
        super.updateEntity();
    }
    
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setString("username", this.playerName);
    }
    
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.playerName = nbtTagCompound.getString("username");
    }
    
    private boolean isPlayerOnline(String playerName)
    {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer())
        {
            return (MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName) != null);
        }
        
        NetHandlerPlayClient netHandlerPlayClient = (Minecraft.getMinecraft()).thePlayer.sendQueue;
        List<GuiPlayerInfo> list = netHandlerPlayClient.playerInfoList;
        
        for (int i = 0; i < list.size(); i++)
        {
            GuiPlayerInfo guiPlayerInfo = list.get(i);
            
            if (guiPlayerInfo.name.toLowerCase().equals(playerName.toLowerCase()))
            {
                return true;
            }
        }
        
        return false;
    }
}