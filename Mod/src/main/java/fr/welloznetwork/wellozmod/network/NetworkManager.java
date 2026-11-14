package fr.welloznetwork.wellozmod.network;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.network.play.client.C00PacketMoney;
import fr.welloznetwork.wellozmod.network.play.client.C01PacketParticle;
import fr.welloznetwork.wellozmod.network.play.client.C02PacketEmote;
import fr.welloznetwork.wellozmod.network.play.client.C03PacketNickname;
import fr.welloznetwork.wellozmod.network.play.client.C04PacketOpenGui;
import fr.welloznetwork.wellozmod.network.play.client.C05PacketScaffolding;
import fr.welloznetwork.wellozmod.network.play.client.C06PacketSpectator;
import fr.welloznetwork.wellozmod.network.play.client.C07PacketExtendedEntityProperties;
import fr.welloznetwork.wellozmod.network.play.client.C08PacketJobXpNotification;
import fr.welloznetwork.wellozmod.network.play.client.C09PacketNotification;
import fr.welloznetwork.wellozmod.network.play.server.S00PacketOnlineDetector;
import fr.welloznetwork.wellozmod.network.play.server.S01PacketBoatMoving;
import fr.welloznetwork.wellozmod.network.play.server.S02PacketBuy;
import fr.welloznetwork.wellozmod.network.play.server.S03PacketMoney;
import fr.welloznetwork.wellozmod.network.play.server.S04PacketElytra;
import fr.welloznetwork.wellozmod.network.play.server.S05PacketCape;
import fr.welloznetwork.wellozmod.network.play.server.S06PacketTeleport;
import fr.welloznetwork.wellozmod.network.play.server.S07PacketGrades;
import fr.welloznetwork.wellozmod.network.play.server.S08PacketEmote;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

@Sharable
public class NetworkManager extends MessageToMessageCodec<FMLProxyPacket, APacket>
{
    private EnumMap<Side, FMLEmbeddedChannel> channels;
    private LinkedList<Class<? extends APacket>> packets = new LinkedList();
    
    protected void encode(ChannelHandlerContext channelHandlerContext, APacket packet, List<Object> out) throws Exception
    {
        ByteBuf byteBuf = Unpooled.buffer();
        
        if (!this.packets.contains(packet.getClass()))
        {
            throw new NullPointerException("No packet registered for: " + packet.getClass().getCanonicalName());
        }
        
        byteBuf.writeByte((byte) this.packets.indexOf((Class<? extends APacket>) packet.getClass()));
        packet.write(channelHandlerContext, byteBuf);
        out.add(new FMLProxyPacket(byteBuf.copy(), (String) channelHandlerContext.channel().attr(NetworkRegistry.FML_CHANNEL).get()));
    }
    
    protected void decode(ChannelHandlerContext channelHandlerContext, FMLProxyPacket fmlProxyPacket, List<Object> out) throws Exception
    {
        ByteBuf byteBuf = fmlProxyPacket.payload();
        Class<? extends APacket> clazz = this.packets.get(byteBuf.readUnsignedByte());
        
        if (clazz == null)
        {
            throw new NullPointerException("No packet registered for discriminator: " + byteBuf.readUnsignedByte());
        }
        
        APacket packet = clazz.newInstance();
        packet.read(channelHandlerContext, byteBuf.slice());
        
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            packet.processClient(this.getEntityPlayer());
        }
        
        else
        {
            INetHandler iNetHandler = channelHandlerContext.channel().attr(NetworkRegistry.NET_HANDLER).get();
            
            if (iNetHandler instanceof NetHandlerPlayServer)
            {
                packet.processServer(((NetHandlerPlayServer) iNetHandler).playerEntity);
            }
        }
    }
    
    public void init()
    {
        this.channels = NetworkRegistry.INSTANCE.newChannel("WelloZNetwork", this);
        
        this.registerPacket(C00PacketMoney.class);
        this.registerPacket(C03PacketNickname.class);
        this.registerPacket(C04PacketOpenGui.class);
        this.registerPacket(C05PacketScaffolding.class);
        this.registerPacket(C06PacketSpectator.class);
        this.registerPacket(C07PacketExtendedEntityProperties.class);
        this.registerPacket(C08PacketJobXpNotification.class);
        this.registerPacket(C09PacketNotification.class);
        
        if (Constants.V2)
        {
            this.registerPacket(C01PacketParticle.class);
            this.registerPacket(C02PacketEmote.class);
            
            this.registerPacket(S04PacketElytra.class);
            this.registerPacket(S05PacketCape.class);
            this.registerPacket(S08PacketEmote.class);
        }
        
        this.registerPacket(S00PacketOnlineDetector.class);
        this.registerPacket(S01PacketBoatMoving.class);
        this.registerPacket(S02PacketBuy.class);
        this.registerPacket(S03PacketMoney.class);
        this.registerPacket(S06PacketTeleport.class);
        this.registerPacket(S07PacketGrades.class);
    }
    
    private boolean registerPacket(Class<? extends APacket> clazz)
    {
        if (this.packets.size() > 256 || this.packets.contains(clazz))
        {
            return false;
        }
        
        this.packets.add(clazz);
        return true;
    }
    
    public void sendToAll(APacket packet)
    {
        ((FMLEmbeddedChannel) this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.ALL);
        ((FMLEmbeddedChannel) this.channels.get(Side.SERVER)).writeAndFlush(packet);
    }
    
    public void sendToPlayer(APacket packet, EntityPlayerMP entityPlayerMP)
    {
        ((FMLEmbeddedChannel) this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.PLAYER);
        ((FMLEmbeddedChannel) this.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entityPlayerMP);
        ((FMLEmbeddedChannel) this.channels.get(Side.SERVER)).writeAndFlush(packet);
    }
    
    public void sendToServer(APacket packet)
    {
        ((FMLEmbeddedChannel) this.channels.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.TOSERVER);
        ((FMLEmbeddedChannel) this.channels.get(Side.CLIENT)).writeAndFlush(packet);
    }
    
    @SideOnly(Side.CLIENT)
    private EntityPlayer getEntityPlayer()
    {
        return (EntityPlayer) (Minecraft.getMinecraft()).thePlayer;
    }
}