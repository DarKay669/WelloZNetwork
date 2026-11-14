package fr.welloznetwork.wellozmod.network.play.client;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.client.notification.Notification;
import fr.welloznetwork.wellozmod.client.notification.NotificationManager;
import fr.welloznetwork.wellozmod.client.notification.NotificationType;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobType;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.CLIENT)
public class C08PacketJobXpNotification extends APacket
{
    private JobType jobType;
    private int xp;
    
    public C08PacketJobXpNotification() {}
    
    public C08PacketJobXpNotification(JobType jobType, int xp)
    {
        this.jobType = jobType;
        this.xp = xp;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.jobType = JobType.values()[byteBuf.readInt()];
        this.xp = byteBuf.readInt();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        byteBuf.writeInt(this.jobType.ordinal());
        byteBuf.writeInt(this.xp);
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        NotificationManager.addNotification(new Notification(NotificationType.JOB, this.xp + " " + I18n.format("gui.jobs.xpEarned") + " " + I18n.format("gui.jobs." + this.jobType.name().toLowerCase()), 2));
    }
    
    public void processServer(EntityPlayer entityPlayer) {}
}