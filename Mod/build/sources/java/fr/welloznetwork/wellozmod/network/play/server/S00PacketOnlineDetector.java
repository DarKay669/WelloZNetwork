package fr.welloznetwork.wellozmod.network.play.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.network.APacket;
import fr.welloznetwork.wellozmod.tileentity.TileEntityOnlineDetector;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.SERVER)
public class S00PacketOnlineDetector extends APacket
{
    private String playerName;
    private TileEntityOnlineDetector tileEntityOnlineDetector;
    private int x, y, z;
    
    public S00PacketOnlineDetector() {}
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.playerName = ByteBufUtils.readUTF8String(byteBuf);
        this.x = byteBuf.readInt();
        this.y = byteBuf.readInt();
        this.z = byteBuf.readInt();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        ByteBufUtils.writeUTF8String(byteBuf, this.playerName);
        byteBuf.writeInt(this.tileEntityOnlineDetector.xCoord);
        byteBuf.writeInt(this.tileEntityOnlineDetector.yCoord);
        byteBuf.writeInt(this.tileEntityOnlineDetector.zCoord);
    }
    
    public void processClient(EntityPlayer entityPlayer) {}
    
    public void processServer(EntityPlayer entityPlayer)
    {
        TileEntityOnlineDetector tileEntityOnlineDetector = (TileEntityOnlineDetector) entityPlayer.worldObj.getTileEntity(this.x, this.y, this.z);
        
        if (tileEntityOnlineDetector != null)
        {
            tileEntityOnlineDetector.setPlayerName(this.playerName);
        }
    }
    
    public void addInformations(String playerName, TileEntityOnlineDetector tileEntityOnlineDetector)
    {
        this.playerName = playerName;
        this.tileEntityOnlineDetector = tileEntityOnlineDetector;
    }
}