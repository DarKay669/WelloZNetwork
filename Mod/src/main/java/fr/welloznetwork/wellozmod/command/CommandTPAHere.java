package fr.welloznetwork.wellozmod.command;

import java.util.List;

import fr.welloznetwork.wellozmod.command.CommandTPA.HereTeleport;
import fr.welloznetwork.wellozmod.command.CommandTPA.Teleport;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandTPAHere extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "tpahere";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.tpaHere.usage";
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            if (args.length != 1)
            {
                throw new WrongUsageException("commands.tpaHere.usage");
            }
            
            EntityPlayerMP entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().func_152612_a(args[0]);
            
            if (entityPlayerMP == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.playerNotFound", args[0]);
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
            }
            
            Teleport teleport = CommandTPA.getReceiverTeleport(iCommandSender.getCommandSenderName());
            
            if (teleport != null)
            {
                CommandTPA.PLAYERS_MAP.remove(teleport);
                CommandTPA.notifyCanceledTeleport(teleport);
            }
            
            HereTeleport hereTeleport = new HereTeleport(iCommandSender.getCommandSenderName(), args[0]);
            CommandTPA.PLAYERS_MAP.put(hereTeleport, 1200);
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpaHere.hasRequested", args[0]);
            ChatComponentTranslation chatComponentTranslation1 = new ChatComponentTranslation("commands.tpa.hasRequested1");
            ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.tpa.hasRequested2", (1200 / 20));
            ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation("commands.tpa.requested", args[0]);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GOLD);
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            
            iCommandSender.addChatMessage(chatComponentTranslation3);
            
            entityPlayerMP.addChatMessage(chatComponentTranslation);
            entityPlayerMP.addChatMessage(chatComponentTranslation1);
            entityPlayerMP.addChatMessage(chatComponentTranslation2);
        }
    }
    
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
}