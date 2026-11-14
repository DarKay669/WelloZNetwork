package fr.welloznetwork.wellozmod.network.play.server;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.network.APacket;
import fr.welloznetwork.wellozmod.world.TeleporterTheAfter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

@Packet(side = Side.SERVER)
public class S06PacketTeleport extends APacket
{
    public S06PacketTeleport() {}
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {}
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {}
    
    public void processClient(EntityPlayer entityPlayer) {}
    
    public void processServer(EntityPlayer entityPlayer)
    {
        MinecraftServer minecraftServer = ((EntityPlayerMP) entityPlayer).mcServer;
        
        if (minecraftServer != null && minecraftServer.worldServerForDimension(2) != null)
        {
            minecraftServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) entityPlayer, 2, new TeleporterTheAfter(minecraftServer.worldServerForDimension(2)));
        }
    }
}