package fr.welloznetwork.wellozmod.command;

import java.util.HashMap;
import java.util.Map;

import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.world.SpawnSavedData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandSpawn extends CommandBase implements ICommandPermission
{
    public static final Map<EntityPlayerMP, TeleportData> COOLDOWN_MAP = new HashMap<>();
    
    public String getCommandName()
    {
        return "spawn";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            int cooldown;
            
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iCommandSender;
            
            int[] data = SpawnSavedData.getSpawn(entityPlayerMP.worldObj).nbtTagCompound.getIntArray("spawn");
            
            if (data.length <= 0)
            {
                throw new WrongUsageException("commands.spawn.notFound");
            }
            
            GradeType gradeType = RegistryGrades.getGrade(entityPlayerMP);
            
            if (gradeType.ordinal() >= gradeType.DONATOR.ordinal())
            {
                cooldown = 0;
            }
            
            else
            {
                cooldown = 60;
            }
            
            COOLDOWN_MAP.put(entityPlayerMP, new TeleportData(cooldown, entityPlayerMP, data));
            
            if (gradeType.ordinal() >= gradeType.DONATOR.ordinal()) {}
            
            else
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.spawn.teleport", (cooldown / 20));
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY);
                
                entityPlayerMP.addChatMessage(chatComponentTranslation);
            }            
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
    
    public static class TeleportData
    {
        public int cooldown;
        public double x, y, z;
        public int[] data;
        
        public TeleportData(int cooldown, EntityPlayerMP entityPlayerMP, int[] data)
        {
            this.cooldown = cooldown;
            this.x = entityPlayerMP.posX;
            this.y = entityPlayerMP.posY;
            this.z = entityPlayerMP.posZ;
            this.data = data;
        }
    }
}