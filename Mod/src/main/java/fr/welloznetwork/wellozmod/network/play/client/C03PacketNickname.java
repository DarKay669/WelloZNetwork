package fr.welloznetwork.wellozmod.network.play.client;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.command.CommandNickname;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

@Packet(side = Side.CLIENT)
public class C03PacketNickname extends APacket
{
    private UUID uuid;
    private String nickname;
    
    public C03PacketNickname() {}
    
    public C03PacketNickname(UUID uuid, String nickname)
    {
        this.uuid = uuid;
        this.nickname = nickname;
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        byteBuf.writeLong(this.uuid.getMostSignificantBits());
        byteBuf.writeLong(this.uuid.getLeastSignificantBits());
        
        byte[] strBytes = this.nickname.getBytes(StandardCharsets.UTF_8);
        
        byteBuf.writeInt(strBytes.length);
        byteBuf.writeBytes(strBytes);
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        long most = byteBuf.readLong();
        long least = byteBuf.readLong();
        
        this.uuid = new UUID(most, least);
        
        int length = byteBuf.readInt();
        byte[] strBytes = new byte[length];
        
        byteBuf.readBytes(strBytes);
        
        this.nickname = new String(strBytes, StandardCharsets.UTF_8);
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        CommandNickname.NICKNAMES.put(this.uuid, this.nickname);
    }
    
    public void processServer(EntityPlayer entityPlayer) 
    {
        CommandNickname.NICKNAMES.put(this.uuid, this.nickname);
        
        for (Object o : entityPlayer.worldObj.playerEntities)
        {
            if (o instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityPlayerMP = (EntityPlayerMP) o;
                
                WelloZMod.proxy.getNetworkManager().sendToPlayer(new C03PacketNickname(this.uuid, this.nickname), entityPlayerMP);
            }
        }
    }
}