package fr.welloznetwork.wellozmod.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public abstract class APacket
{
    public abstract void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf);
    
    public abstract void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf);
    
    public abstract void processClient(EntityPlayer entityPlayer);
    
    public abstract void processServer(EntityPlayer entityPlayer);
}