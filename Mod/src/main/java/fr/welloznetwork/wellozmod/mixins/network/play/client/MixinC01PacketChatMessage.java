package fr.welloznetwork.wellozmod.mixins.network.play.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.base.Charsets;

import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Mixin(C01PacketChatMessage.class)
public class MixinC01PacketChatMessage
{
    @Shadow
    private String field_149440_a;
    
    @Redirect(method = "<init>(Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Ljava/lang/String;substring(II)Ljava/lang/String;"))
    private String redirectSubstring(String message, int beginIndex, int endIndex)
    {
        return message;
    }
    
    @Overwrite
    public void readPacketData(PacketBuffer packetBuffer) throws IOException
    {
        packetBuffer.readerIndex(packetBuffer.readerIndex() + packetBuffer.readVarIntFromBuffer());
        
        this.field_149440_a = packetBuffer.toString(packetBuffer.readerIndex(), packetBuffer.readVarIntFromBuffer(), StandardCharsets.UTF_8);
    }
    
    @Overwrite
    public void writePacketData(PacketBuffer packetBuffer) throws IOException
    {
        byte[] abyte = this.field_149440_a.getBytes(Charsets.UTF_8);
        
        packetBuffer.writeVarIntToBuffer(abyte.length);
        packetBuffer.writeBytes(abyte);
    }
    
    @Inject(method = "processPacket(Lnet/minecraft/network/INetHandler;)V", at = @At("HEAD"))
    private void processPacket(INetHandler iNetHandler, CallbackInfo callbackInfo)
    {
        if (!(iNetHandler instanceof NetHandlerPlayServer))
        {
            return;
        }
        
        NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer) iNetHandler;
        
        if (this.field_149440_a.length() > 100)
        {
            this.field_149440_a = this.field_149440_a.substring(0, 100);
        }
    }
}