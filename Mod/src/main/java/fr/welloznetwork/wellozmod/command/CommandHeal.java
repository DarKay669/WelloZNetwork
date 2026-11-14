package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandHeal extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "heal";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender.getEntityWorld().getPlayerEntityByName(iCommandSender.getCommandSenderName());
            
            if (entityPlayer != null && entityPlayer.getHealth() == 20.0F)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.heal.full");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayer.addChatMessage(chatComponentTranslation);
            }

            else 
            {
                entityPlayer.setHealth(20.0F);
                
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.heal.healthed");
                
                entityPlayer.addChatMessage(chatComponentTranslation);
            }
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
}