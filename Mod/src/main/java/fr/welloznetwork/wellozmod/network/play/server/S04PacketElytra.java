package fr.welloznetwork.wellozmod.network.play.server;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.item.ItemElytra;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@Packet(side = Side.SERVER)
public class S04PacketElytra extends APacket
{
    public S04PacketElytra() {}
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {}
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {}
    
    public void processClient(EntityPlayer entityPlayer) {}
    
    public void processServer(EntityPlayer entityPlayer)
    {
        if (!entityPlayer.onGround && (entityPlayer.motionY < 0.0D) && !((IEntityPlayer) entityPlayer).isElytraFlying() && !entityPlayer.isInWater())
        {
            ItemStack itemStack = ItemElytra.getElytra(entityPlayer);
            
            if (itemStack != null && itemStack.getItem() instanceof ItemElytra && !ItemElytra.isBroken(itemStack))
            {
                ((IEntityPlayer) entityPlayer).setElytraFlying(true);
            }
        }
        
        else
        {
            ((IEntityPlayer) entityPlayer).setElytraFlying(true);
            ((IEntityPlayer) entityPlayer).setElytraFlying(false);
        }
    }
}