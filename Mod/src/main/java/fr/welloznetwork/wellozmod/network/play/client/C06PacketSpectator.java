package fr.welloznetwork.wellozmod.network.play.client;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.CLIENT)
public class C06PacketSpectator extends APacket
{
    private Minecraft mc;
    private boolean noClip;
    
    public C06PacketSpectator() {}
    
    public C06PacketSpectator(boolean noClip)
    {
        this.noClip = noClip;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.noClip = byteBuf.readBoolean();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        byteBuf.writeBoolean(this.noClip);
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        this.mc.thePlayer.noClip = this.noClip;
        
        if (this.noClip)
        {
            this.mc.thePlayer.capabilities.isFlying = true;
            this.mc.thePlayer.capabilities.allowFlying = true;
            this.mc.thePlayer.sendPlayerAbilities();
        }
    }
    
    public void processServer(EntityPlayer entityPlayer) {}
}