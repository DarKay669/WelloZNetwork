package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.network.play.client.C09PacketNotification;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandNotification extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "notification";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.notification.usage";
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("notif");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (args.length <= 3)
        {
            throw new WrongUsageException("commands.notification.usage");
        }
        
        int time = 0;
        
        try
        {
            time = Integer.parseInt(args[2]);
        }
        
        catch (NumberFormatException e)
        {
            throw new WrongUsageException("commands.notification.usage");
        }
        
        args[0] = args[0].replaceAll("_", " ");
        args[0] = args[0].replaceAll("&", "�");
        args[1] = args[1].replaceAll("_", " ");
        args[1] = args[1].replaceAll("&", "�");
        
        C09PacketNotification packet = new C09PacketNotification(args[0], args[1], time);
        
        if (args[3].equalsIgnoreCase("@all"))
        {
            WelloZMod.proxy.getNetworkManager().sendToAll(packet);
        }
        
        else
        {
            List<EntityPlayer> entityPlayers = (MinecraftServer.getServer().getConfigurationManager()).playerEntityList;
            
            for (EntityPlayer entityPlayer : entityPlayers)
            {
                if (!entityPlayer.getCommandSenderName().equalsIgnoreCase(args[3]))
                {
                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.playerNotFound", args[3]);
                    chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                    
                    iCommandSender.addChatMessage(chatComponentTranslation);
                }
                
                else
                {
                    WelloZMod.proxy.getNetworkManager().sendToPlayer(packet, (EntityPlayerMP) entityPlayer);
                }
            }
        }
    }
    
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args)
    {
        return args.length == 4 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 3;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.STAFF;
    }
}