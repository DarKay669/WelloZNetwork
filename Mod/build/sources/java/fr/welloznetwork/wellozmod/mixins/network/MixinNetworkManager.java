package fr.welloznetwork.wellozmod.mixins.network;

import java.net.InetAddress;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;

@Mixin(NetworkManager.class)
public class MixinNetworkManager
{
    @Overwrite
    @SideOnly(Side.CLIENT)
    public static NetworkManager provideLanClient(InetAddress inetAddress, int serverPort)
    {
        final NetworkManager networkManager = new NetworkManager(true);
        ((Bootstrap) ((Bootstrap) ((Bootstrap) (new Bootstrap()).group(NetworkManager.eventLoops)).handler(new ChannelInitializer()
        {
            protected void initChannel(Channel channel)
            {
                try
                {
                    channel.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
                }
                
                catch (ChannelException channelException) {;}
                
                try
                {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
                }
                
                catch (ChannelException channelException) {;}
                
                channel.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(NetworkManager.field_152462_h)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(NetworkManager.field_152462_h)).addLast("packet_handler", networkManager);
            }
        })).channel(NioSocketChannel.class)).connect(inetAddress, serverPort).syncUninterruptibly();
                
        return networkManager;
    }
}