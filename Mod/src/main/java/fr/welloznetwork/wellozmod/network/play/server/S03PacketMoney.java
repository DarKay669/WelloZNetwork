package fr.welloznetwork.wellozmod.network.play.server;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.entity.player.CustomEntityPlayer;
import fr.welloznetwork.wellozmod.network.APacket;
import fr.welloznetwork.wellozmod.network.play.client.C00PacketMoney;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

@Packet(side = Side.SERVER)
public class S03PacketMoney extends APacket
{
    private int amount;
    
    public S03PacketMoney() {}
    
    public S03PacketMoney(int amount)
    {
        this.amount = amount;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) 
    {
        this.amount = byteBuf.readInt();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) 
    {
        byteBuf.writeInt(this.amount);
    }
    
    public void processClient(EntityPlayer entityPlayer) {}
    
    public void processServer(EntityPlayer entityPlayer) 
    {
        CustomEntityPlayer customEntityPlayer = CustomEntityPlayer.get(entityPlayer);
        
        if (this.amount > 0)
        {
            customEntityPlayer.addMoney(this.amount);
        }
        
        else
        {
            customEntityPlayer.decreaseMoney(-this.amount);
        }
        
        WelloZMod.proxy.getNetworkManager().sendToPlayer(new C00PacketMoney(customEntityPlayer.getMoney()), (EntityPlayerMP) entityPlayer);
    }
}