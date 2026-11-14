package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandListPlayers extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "list";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.list.players", Integer.valueOf(MinecraftServer.getServer().getCurrentPlayerCount()), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers()));
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GOLD);
        
        iCommandSender.addChatMessage(chatComponentTranslation);
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}