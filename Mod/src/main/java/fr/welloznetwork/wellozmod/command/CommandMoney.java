package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.entity.player.CustomEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandMoney extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "money";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("bal", "balance");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender.getEntityWorld().getPlayerEntityByName(iCommandSender.getCommandSenderName());
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.money.wallet", new ChatComponentText("" + EnumChatFormatting.GREEN + CustomEntityPlayer.get(entityPlayer).getMoney() + "$"));
            
            entityPlayer.addChatMessage(chatComponentTranslation);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}