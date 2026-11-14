package fr.welloznetwork.wellozmod.network.play.client;

import java.io.IOException;
import java.util.UUID;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.network.APacket;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedEntityProperties;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

@Packet(side = Side.CLIENT)
public class C07PacketExtendedEntityProperties extends APacket
{
    private UUID uuid;
    private String propertyName;
    private NBTTagCompound nbtTagCompound;
    
    public C07PacketExtendedEntityProperties() {}
    
    public C07PacketExtendedEntityProperties(UUID uuid, String propertyName, NBTTagCompound nbtTagCompound)
    {
        this.uuid = uuid;
        this.propertyName = propertyName;
        this.nbtTagCompound = nbtTagCompound;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.uuid = UUID.fromString(ByteBufUtils.readUTF8String(byteBuf));
        this.propertyName = ByteBufUtils.readUTF8String(byteBuf);
        
        try
        {
            this.nbtTagCompound = new PacketBuffer(byteBuf).readNBTTagCompoundFromBuffer();
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        ByteBufUtils.writeUTF8String(byteBuf, this.uuid.toString());
        ByteBufUtils.writeUTF8String(byteBuf, this.propertyName);
        
        try
        {
            new PacketBuffer(byteBuf).writeNBTTagCompoundToBuffer(this.nbtTagCompound);
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        if (this.propertyName == null)
        {
            return;
        }
        
        entityPlayer = (Minecraft.getMinecraft()).thePlayer.worldObj.func_152378_a(this.uuid);
        
        if (entityPlayer == null)
        {
            return;
        }
        
        ExtendedEntityProperties extendedEntityProperties = (ExtendedEntityProperties) entityPlayer.getExtendedProperties(this.propertyName);
        
        if (extendedEntityProperties == null)
        {
            return;
        }
        
        extendedEntityProperties.loadNBTData(this.nbtTagCompound);
    }
    
    public void processServer(EntityPlayer entityPlayer) {}   
}