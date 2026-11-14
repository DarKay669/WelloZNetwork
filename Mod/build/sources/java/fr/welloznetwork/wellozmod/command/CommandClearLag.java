package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandClearLag extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "clearlag";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("lag");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            if (args.length <= 0)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.clearlag.nextClearLag", WelloZMod.proxy.getServerEventRegister().getTimeBeforeClearLag());
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GOLD);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
            }
            
            else if (args.length == 1 && args[0].equalsIgnoreCase("force"))
            {
                GradeType gradeType = RegistryGrades.getGrade((EntityPlayerMP) iCommandSender);
                
                if (gradeType.ordinal() >= gradeType.STAFF.ordinal())
                {
                    WelloZMod.proxy.getServerEventRegister().clearLag();
                    WelloZMod.proxy.getServerEventRegister().resetClearLagCounter();
                    MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentTranslation("commands.clearlag.removedEntities"));
                }
                
                else
                {
                    throw new WrongUsageException("commands.generic.permission");
                }
            }
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}