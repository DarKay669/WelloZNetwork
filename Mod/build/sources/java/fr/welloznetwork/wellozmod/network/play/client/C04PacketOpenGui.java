package fr.welloznetwork.wellozmod.network.play.client;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.client.ui.UISleepingDeeply;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.CLIENT)
public class C04PacketOpenGui extends APacket
{
    public C04PacketOpenGui() {}
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {}
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)  {}
    
    public void processClient(EntityPlayer entityPlayer) 
    {
        Minecraft.getMinecraft().displayGuiScreen(new UISleepingDeeply(entityPlayer));
    }
    
    public void processServer(EntityPlayer entityPlayer) {}
}