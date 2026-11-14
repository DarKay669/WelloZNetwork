package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.mixins.util.IMixinFoodStatsAccessor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandFeed extends CommandBase implements ICommandPermission
{    
    public String getCommandName()
    {
        return "feed";
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
            
            if (entityPlayer != null && entityPlayer.getFoodStats().getFoodLevel() == 20)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.feed.full");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayer.addChatMessage(chatComponentTranslation);
            }
            
            else 
            {            
                ((IMixinFoodStatsAccessor) entityPlayer.getFoodStats()).setFoodLevel(20);
                
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.feed.feeded");
                
                entityPlayer.addChatMessage(chatComponentTranslation);
            }
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
}