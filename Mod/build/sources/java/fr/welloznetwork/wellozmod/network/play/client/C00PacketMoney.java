package fr.welloznetwork.wellozmod.network.play.client;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.client.ui.UIShop;
import fr.welloznetwork.wellozmod.entity.player.CustomEntityPlayer;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.CLIENT)
public class C00PacketMoney extends APacket
{
    private int money;
    
    public C00PacketMoney() {}
    
    public C00PacketMoney(int money)
    {
        this.money = money;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.money = byteBuf.readInt();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        byteBuf.writeInt(this.money);
    }
    
    public void processClient(EntityPlayer entityPlayer) 
    {
        CustomEntityPlayer.get(entityPlayer).setMoney(this.money);
        
        if (Minecraft.getMinecraft().currentScreen instanceof UIShop)
        {
            ((UIShop) Minecraft.getMinecraft().currentScreen).setMoney(this.money);
        }
    }
    
    public void processServer(EntityPlayer entityPlayer) {}
}