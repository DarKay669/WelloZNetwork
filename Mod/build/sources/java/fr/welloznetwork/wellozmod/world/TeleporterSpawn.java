package fr.welloznetwork.wellozmod.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterSpawn extends Teleporter
{
    public TeleporterSpawn(WorldServer worldServer)
    {
        super(worldServer);
    }
    
    public void placeInPortal(Entity entity, double x, double y, double z, float rotationYaw)
    {
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entity;
        EntityPlayer entityPlayer = (EntityPlayer) entity;
        SpawnSavedData spawnSavedData = SpawnSavedData.getSpawn(entityPlayer.worldObj);
        
        int[] data = spawnSavedData.nbtTagCompound.getIntArray("spawn");
        
        float posX = data[0];
        float posY = data[1];
        float posZ = data[2];
        float yaw = data[3];
        
        entityPlayerMP.setLocationAndAngles(posX + 0.5D, posY + 0.5D, posZ + 0.5D, yaw, 0.0F);
        entityPlayerMP.setPositionAndUpdate(posX + 0.5D, posY + 0.5D, posZ + 0.5D);
        entityPlayerMP.setRotationYawHead(yaw);
        
        entity.motionX = 0.0D;
        entity.motionY = 0.0D;
        entity.motionZ = 0.0D;
    }
    
    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float rotationYaw)
    {
        return false;
    }
}