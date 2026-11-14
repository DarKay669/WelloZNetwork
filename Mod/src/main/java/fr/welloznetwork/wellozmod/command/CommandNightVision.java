package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;

public class CommandNightVision extends CommandBase implements ICommandPermission
{
    private static boolean isNightVision = false;
    
    public String getCommandName()
    {
        return "nightvision";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("nv");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.nightVision.changed");
            
            iCommandSender.addChatMessage(chatComponentTranslation);
            
            isNightVision = !isNightVision;
        }
    }
    
    public static boolean isNightVision()
    {
        return isNightVision;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
}