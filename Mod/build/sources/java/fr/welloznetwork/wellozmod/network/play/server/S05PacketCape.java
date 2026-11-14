package fr.welloznetwork.wellozmod.network.play.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryCapes;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

@Packet(side = Side.SERVER)
public class S05PacketCape extends APacket
{
    private String playerName, capeName;
    
    public S05PacketCape() {}
    
    public S05PacketCape(String playerName, String capeName)
    {
        this.playerName = playerName;
        this.capeName = capeName;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) 
    {
        this.playerName = ByteBufUtils.readUTF8String(byteBuf);
        this.capeName = ByteBufUtils.readUTF8String(byteBuf);
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) 
    {
        ByteBufUtils.writeUTF8String(byteBuf, this.playerName != null ? this.playerName : "");
        ByteBufUtils.writeUTF8String(byteBuf, this.capeName != null ? this.capeName : "");
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        RegistryCapes.setCape(this.playerName, RegistryCapes.setCape(this.capeName));
    }
    
    public void processServer(EntityPlayer entityPlayer) 
    {
        RegistryCapes.setCape(entityPlayer.getCommandSenderName(), RegistryCapes.setCape(this.capeName));
        
        for (Object o : entityPlayer.worldObj.playerEntities)
        {
            if (o instanceof EntityPlayerMP)
            {
                WelloZMod.proxy.getNetworkManager().sendToPlayer(new S05PacketCape(entityPlayer.getCommandSenderName(), this.capeName), (EntityPlayerMP) o);
            }
        }
    }
}