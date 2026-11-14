package fr.welloznetwork.wellozmod.network.play.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.venus.lib.packet.Packet;
import fr.welloznetwork.wellozmod.network.APacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@Packet(side = Side.CLIENT)
public class C01PacketParticle extends APacket
{
    private float posX, posY, posZ;
    
    public C01PacketParticle() {}
    
    public C01PacketParticle(float posX, float posY, float posZ)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    
    public void read(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) 
    {
        this.posX = byteBuf.readFloat();
        this.posY = byteBuf.readFloat();
        this.posZ = byteBuf.readFloat();
    }
    
    public void write(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) 
    {
        byteBuf.writeFloat(this.posX);
        byteBuf.writeFloat(this.posY);
        byteBuf.writeFloat(this.posZ);
    }
    
    public void processClient(EntityPlayer entityPlayer)
    {
        WorldClient worldClient = FMLClientHandler.instance().getWorldClient();
        
        if (worldClient == null)
        {
            return;
        }
        
        if (((World) worldClient).isRemote)
        {
            for (int angle = 0; angle < 360; angle += 4)
            {
                double angle1 = angle * Math.PI / 180.0D;
                
                spawnParticles("cloud", worldClient, this.posX + -MathHelper.sin((float) angle1) * 0.5D, this.posY + 0.5D, this.posZ + MathHelper.cos((float) angle1) * 0.5D, 0.0D, 0.0D, 0.0D);
                spawnParticles("cloud", worldClient, this.posX + -MathHelper.sin((float) angle1) * 0.5D, this.posY + MathHelper.cos((float) angle1) * 0.5D, this.posZ + 0.5D, 0.0D, 0.0D, 0.0D);
                spawnParticles("cloud", worldClient, this.posX + 0.5D, this.posY + -MathHelper.sin((float) angle1) * 0.5D, this.posZ + MathHelper.cos((float) angle1) * 0.5D, 0.0D, 0.0D, 0.0D);
                
                spawnParticles("bubbles", worldClient, this.posX + -MathHelper.sin((float) angle1) * 1.5D, this.posY + 0.5D, this.posZ + MathHelper.cos((float) angle1) * 1.5D, 0.0D, 0.0D, 0.0D);
                spawnParticles("bubbles", worldClient, this.posX + -MathHelper.sin((float) angle1) * 1.5D, this.posY + MathHelper.cos((float) angle1) * 1.5D, this.posZ + 0.5D, 0.0D, 0.0D, 0.0D);
                spawnParticles("bubbles", worldClient, this.posX + 0.5D, this.posY + -MathHelper.sin((float) angle1) * 1.5D, this.posZ + MathHelper.cos((float) angle1) * 1.5D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    public void processServer(EntityPlayer entityPlayer) {}
    
    public static void spawnParticles(String particleName, World world, double x, double y, double z, double vecX, double vecY, double vecZ)
    {        
        if (particleName.equals("bubbles"))
        {
            EntityBubbleFX entityBubbleFX = new EntityBubbleFX(world, x, y, z, vecX, vecY, vecZ);
        }
        
        if (particleName.equals("cloud"))
        {
            EntityCloudFX entityCloudFX = new EntityCloudFX(world, x, y, z, vecX, vecY, vecZ);
        }
    }
}