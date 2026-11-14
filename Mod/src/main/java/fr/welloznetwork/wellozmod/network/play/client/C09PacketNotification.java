package fr.welloznetwork.wellozmod.network.play.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.client.notification.Notification;
import fr.welloznetwork.wellozmod.client.notification.NotificationManager;
import fr.welloznetwork.wellozmod.client.notification.NotificationType;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.CLIENT)
public class C09PacketNotification extends APacket
{
    private String title, message;
    private int time;
    
    public C09PacketNotification() {}
    
    public C09PacketNotification(String title, String message, int time)
    {
        this.title = title;
        this.message = message;
        this.time = time;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.title = ByteBufUtils.readUTF8String(byteBuf);
        this.message = ByteBufUtils.readUTF8String(byteBuf);
        this.time = byteBuf.readInt();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        ByteBufUtils.writeUTF8String(byteBuf, this.title != null ? this.title : "");
        ByteBufUtils.writeUTF8String(byteBuf, this.message != null ? this.message : "");
        byteBuf.writeInt(this.time);
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        NotificationManager.addNotification(new Notification(NotificationType.SERVER, this.title, this.message, this.time));
    }
    
    public void processServer(EntityPlayer entityPlayer) {}
}