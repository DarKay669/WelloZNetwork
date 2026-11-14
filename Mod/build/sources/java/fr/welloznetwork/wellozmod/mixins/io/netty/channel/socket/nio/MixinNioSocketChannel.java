package fr.welloznetwork.wellozmod.mixins.io.netty.channel.socket.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

@Mixin(value = NioSocketChannel.class, remap = false)
public abstract class MixinNioSocketChannel extends AbstractNioByteChannel
{
    MixinNioSocketChannel(Channel channel, SelectableChannel selectableChannel)
    {
        super(channel, selectableChannel);
    }
    
    @Overwrite
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception
    {
        for (;;)
        {
            final int msgCount = channelOutboundBuffer.size();
            
            if (msgCount <= 1)
            {
                super.doWrite(channelOutboundBuffer);
                
                return;
            }
            
            ByteBuffer[] byteBuffer = channelOutboundBuffer.nioBuffers();
            
            if (byteBuffer == null)
            {
                super.doWrite(channelOutboundBuffer);
                
                return;
            }
            
            int bufferCount = channelOutboundBuffer.nioBufferCount();
            long expectedWritterByte = channelOutboundBuffer.nioBufferSize();
            
            final SocketChannel socketChannel = (SocketChannel) this.javaChannel();
            
            long writterBytes = 0L;
            boolean done = false;
            
            for (int i = this.config().getWriteSpinCount() - 1; i >= 0; i--)
            {
                long localWritterBytes = 0l;
                
                try
                {
                    localWritterBytes = socketChannel.write(byteBuffer, 0, bufferCount);
                }
                
                catch (IOException e)
                {
                    this.close();
                }
                
                if (localWritterBytes == 0)
                {
                    break;
                }
                
                expectedWritterByte -= localWritterBytes;
                writterBytes += localWritterBytes;
                
                if (expectedWritterByte == 0L)
                {
                    done = true;
                    break;
                }
            }
            
            if (done)
            {
                for (int i = msgCount; i > 0; i--)
                {
                    channelOutboundBuffer.remove();
                }
                
                if (channelOutboundBuffer.isEmpty())
                {
                    this.clearOpWrite();
                    break;
                }
            }
            
            else
            {
                for (int i = msgCount; i > 0; i--)
                {
                    final ByteBuf byteBuf = (ByteBuf) channelOutboundBuffer.current();
                    final int readerIndex = byteBuf.readerIndex();
                    final int readableBytes = byteBuf.writerIndex() - readerIndex;
                    
                    if (readableBytes < writterBytes)
                    {
                        channelOutboundBuffer.progress(readableBytes);
                        channelOutboundBuffer.remove();
                        
                        writterBytes -= readableBytes;
                    }
                    
                    else if (readableBytes > writterBytes)
                    {
                        byteBuf.readerIndex(readerIndex + (int) writterBytes);
                        channelOutboundBuffer.progress(writterBytes);
                        break;
                    }
                    
                    else
                    {
                        channelOutboundBuffer.progress(readableBytes);
                        channelOutboundBuffer.remove();
                        break;
                    }
                }
                
                this.setOpWrite();
                break;
            }
        }
    }
}