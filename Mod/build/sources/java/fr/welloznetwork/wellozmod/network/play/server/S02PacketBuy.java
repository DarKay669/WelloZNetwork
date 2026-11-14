package fr.welloznetwork.wellozmod.network.play.server;

import cpw.mods.fml.common.network.ByteBufUtils;
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
import net.minecraft.item.ItemStack;

@Packet(side = Side.SERVER)
public class S02PacketBuy extends APacket
{
    private ItemStack itemStack;
    private int price;
    
    public S02PacketBuy() {}
    
    public S02PacketBuy(ItemStack itemStack, int price)
    {
        this.itemStack = itemStack;
        this.price = price;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.itemStack = ByteBufUtils.readItemStack(byteBuf);
        this.price = byteBuf.readInt();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        ByteBufUtils.writeItemStack(byteBuf, this.itemStack);
        byteBuf.writeInt(this.price);
    }
    
    public void processClient(EntityPlayer entityPlayer) {}
    
    public void processServer(EntityPlayer entityPlayer) 
    {
        CustomEntityPlayer customEntityPlayer = CustomEntityPlayer.get(entityPlayer);
        
        if (customEntityPlayer.getMoney() >= this.price)
        {
            customEntityPlayer.decreaseMoney(this.price);
            
            entityPlayer.inventory.addItemStackToInventory(this.itemStack.copy());
            
            WelloZMod.proxy.getNetworkManager().sendToPlayer(new C00PacketMoney(customEntityPlayer.getMoney()), (EntityPlayerMP) entityPlayer);
        }
    }
}