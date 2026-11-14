package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandLobby extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "lobby";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("hub");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender;
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.lobby.connected");
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GREEN);
            
            entityPlayer.addChatMessage(chatComponentTranslation);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}