package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.command.CommandTPA.Teleport;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandTPDeny extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "tpdeny";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            Teleport teleport = CommandTPA.getReceiverTeleport(iCommandSender.getCommandSenderName());
            
            if (teleport == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpAccept.noRequest");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
                
                return;
            }
            
            CommandTPA.PLAYERS_MAP.remove(teleport);
            
            EntityPlayerMP sender = MinecraftServer.getServer().getConfigurationManager().func_152612_a(teleport.getSender());
            
            if (sender != null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpDeny.cancelled");
                
                sender.addChatMessage(chatComponentTranslation);
            }
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpDeny.hasCancelled", teleport.getReceiver());
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GOLD);
            
            iCommandSender.addChatMessage(chatComponentTranslation);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}