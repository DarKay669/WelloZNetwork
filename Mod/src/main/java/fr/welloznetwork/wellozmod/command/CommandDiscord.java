package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;

public class CommandDiscord extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "discord";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender;
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.discord.link");
            
            iCommandSender.addChatMessage(chatComponentTranslation);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}