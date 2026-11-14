package fr.welloznetwork.wellozmod.network.play.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.network.APacket;
import fr.welloznetwork.wellozmod.network.play.client.C02PacketEmote;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.SERVER)
public class S08PacketEmote extends APacket
{
    private String emoteId;
    
    public S08PacketEmote() {}
    
    public S08PacketEmote(String emoteId)
    {
        this.emoteId = emoteId;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.emoteId = ByteBufUtils.readUTF8String(byteBuf);
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        ByteBufUtils.writeUTF8String(byteBuf, this.emoteId);
    }
    
    public void processClient(EntityPlayer entityPlayer) {}
    
    public void processServer(EntityPlayer entityPlayer)
    {
        if (this.emoteId != null)
        {
            WelloZMod.proxy.getNetworkManager().sendToAll(new C02PacketEmote(this.emoteId, entityPlayer.getCommandSenderName()));
        }
    }
}