package fr.welloznetwork.wellozmod.network.play.server;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

@Packet(side = Side.SERVER)
public class S01PacketBoatMoving extends APacket
{
    private int dimensionId, entityId;
    private double x, y, z;
    private float yaw, pitch;
    private NetHandlerPlayServer netHandlerPlayServer;
    
    public S01PacketBoatMoving() {}
    
    public S01PacketBoatMoving(Entity entity)
    {
        this.dimensionId = entity.worldObj.provider.dimensionId;
        this.entityId = entity.getEntityId();
        this.x = entity.posX;
        this.y = entity.posY;
        this.z = entity.posZ;
        this.yaw = entity.rotationYaw;
        this.pitch = entity.rotationPitch;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        this.dimensionId = byteBuf.readInt();
        this.entityId = byteBuf.readInt();
        this.x = byteBuf.readDouble();
        this.y = byteBuf.readDouble();
        this.z = byteBuf.readDouble();
        this.yaw = byteBuf.readFloat();
        this.pitch = byteBuf.readFloat();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
    {
        byteBuf.writeInt(this.dimensionId);
        byteBuf.writeInt(this.entityId);
        byteBuf.writeDouble(this.x);
        byteBuf.writeDouble(this.y);
        byteBuf.writeDouble(this.z);
        byteBuf.writeFloat(this.yaw);
        byteBuf.writeFloat(this.pitch);
    }
    
    public void processClient(EntityPlayer entityPlayer) {}
    
    public void processServer(EntityPlayer entityPlayer)
    {
        WorldServer worldServer = DimensionManager.getWorld(this.dimensionId);
        
        if (worldServer != null)
        {
            Entity entity = worldServer.getEntityByID(this.entityId);
            
            if (entity == null)
            {
                return;
            }
            
            if (entity.riddenByEntity != this.netHandlerPlayServer.playerEntity)
            {
                return;
            }
            
            double exceptedDelta = entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ;
            double deltaX = this.x - entity.posX;
            double deltaY = this.y - entity.posY;
            double deltaZ = this.z - entity.posZ;
            double actualDelta = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
            
            MinecraftServer minecraftServer = MinecraftServer.getServer();
            
            if (Math.abs(exceptedDelta - actualDelta) > 100.0D && !minecraftServer.isSinglePlayer())
            {
                entity.setPositionAndRotation(entity.posX - 1.0D, entity.posY, entity.posZ - 1.0D, entity.rotationYaw, entity.rotationPitch);
                this.netHandlerPlayServer.sendPacket(new S18PacketEntityTeleport(entity));
                return;
            }
            
            entity.setPositionAndRotation(this.x, this.y, this.z, this.yaw, this.pitch);
            this.netHandlerPlayServer.sendPacket(new S18PacketEntityTeleport(entity));
        }
        
        return;
    }
}