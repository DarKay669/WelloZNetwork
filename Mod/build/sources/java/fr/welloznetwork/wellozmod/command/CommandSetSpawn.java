package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.world.SpawnSavedData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;

public class CommandSetSpawn extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "setspawn";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iCommandSender;
            ChunkCoordinates chunkCoordinates = iCommandSender.getPlayerCoordinates();
            WorldServer worldServer = (WorldServer) entityPlayerMP.worldObj;
            SpawnSavedData spawnSavedData = SpawnSavedData.getSpawn(worldServer);
            
            int[] arrayToStore = new int[5];
            
            arrayToStore[0] = chunkCoordinates.posX;
            arrayToStore[1] = chunkCoordinates.posY;
            arrayToStore[2] = chunkCoordinates.posZ;
            arrayToStore[3] = (int) entityPlayerMP.rotationYaw;
            arrayToStore[4] = worldServer.provider.dimensionId;
            
            spawnSavedData.nbtTagCompound.setIntArray("spawn", arrayToStore);
            spawnSavedData.markDirty();
            
            entityPlayerMP.worldObj.setSpawnLocation(chunkCoordinates.posX, chunkCoordinates.posY, chunkCoordinates.posZ);
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.setSpawn.set");
            
            entityPlayerMP.addChatMessage(chatComponentTranslation);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.ADMINISTRATOR;
    }
}