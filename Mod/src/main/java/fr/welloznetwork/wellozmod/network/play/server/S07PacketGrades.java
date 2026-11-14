package fr.welloznetwork.wellozmod.network.play.server;

import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

@Packet(side = Side.SERVER)
public class S07PacketGrades extends APacket
{
    private UUID uuid;
    private GradeType gradeType;
    
    public S07PacketGrades() {}
    
    public S07PacketGrades(UUID uuid, GradeType gradeType)
    {
        this.uuid = uuid;
        this.gradeType = gradeType;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.uuid = new UUID(byteBuf.readLong(), byteBuf.readLong());
        this.gradeType = GradeType.getByID(byteBuf.readInt());
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) 
    {
        byteBuf.writeLong(this.uuid.getMostSignificantBits());
        byteBuf.writeLong(this.uuid.getLeastSignificantBits());
        byteBuf.writeInt(this.gradeType.getID());
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        RegistryGrades.setGrade(this.uuid, this.gradeType);
    }
    
    public void processServer(EntityPlayer entityPlayer)
    {
        RegistryGrades.setGrade(this.uuid, this.gradeType);
    }
}