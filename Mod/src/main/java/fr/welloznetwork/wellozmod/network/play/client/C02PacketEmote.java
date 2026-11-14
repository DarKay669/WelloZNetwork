package fr.welloznetwork.wellozmod.network.play.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.CLIENT)
public class C02PacketEmote extends APacket
{
    public String emoteName, playerName;
    
    public C02PacketEmote() {}
    
    public C02PacketEmote(String emoteName, String playerName)
    {
        this.emoteName = emoteName;
        this.playerName = playerName;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.emoteName = ByteBufUtils.readUTF8String(byteBuf);
        this.playerName = ByteBufUtils.readUTF8String(byteBuf);
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        ByteBufUtils.writeUTF8String(byteBuf, this.emoteName);
        ByteBufUtils.writeUTF8String(byteBuf, this.playerName);
    }
    
    public void processClient(EntityPlayer entityPlayer) 
    {
        WelloZMod.proxy.registerEmotePacket(this);
    }
    
    public void processServer(EntityPlayer entityPlayer) {}
}