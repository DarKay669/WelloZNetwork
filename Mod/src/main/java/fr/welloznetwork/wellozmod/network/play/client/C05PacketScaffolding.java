package fr.welloznetwork.wellozmod.network.play.client;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.CLIENT)
public class C05PacketScaffolding extends APacket
{
    private double posX, posY, posZ;
    
    public C05PacketScaffolding() {}
    
    public C05PacketScaffolding(double posX, double posY, double posZ)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.posX = byteBuf.readDouble();
        this.posY = byteBuf.readDouble();
        this.posZ = byteBuf.readDouble();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        byteBuf.writeDouble(this.posX);
        byteBuf.writeDouble(this.posY);
        byteBuf.writeDouble(this.posZ);
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        entityPlayer.setPositionAndUpdate(this.posX, this.posY, this.posZ);
        entityPlayer.playSound("random.orb", 10.0F, 1.0F);
    }
    
    public void processServer(EntityPlayer entityPlayer) 
    {
        entityPlayer.setPositionAndUpdate(this.posX, this.posY, this.posZ);
    }
}