package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandColors extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "couleurs";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("colors");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.colors.header");
            ChatComponentTranslation chatComponentTranslation1 = new ChatComponentTranslation("commands.colors.black");
            ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.colors.darkBlue");
            ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation("commands.colors.darkGreen");
            ChatComponentTranslation chatComponentTranslation4 = new ChatComponentTranslation("commands.colors.darkAqua");
            ChatComponentTranslation chatComponentTranslation5 = new ChatComponentTranslation("commands.colors.darkRed");
            ChatComponentTranslation chatComponentTranslation6 = new ChatComponentTranslation("commands.colors.darkPurple");
            ChatComponentTranslation chatComponentTranslation7 = new ChatComponentTranslation("commands.colors.gold");
            ChatComponentTranslation chatComponentTranslation8 = new ChatComponentTranslation("commands.colors.gray");
            ChatComponentTranslation chatComponentTranslation9 = new ChatComponentTranslation("commands.colors.darkGray");
            ChatComponentTranslation chatComponentTranslation10 = new ChatComponentTranslation("commands.colors.blue");
            ChatComponentTranslation chatComponentTranslation11 = new ChatComponentTranslation("commands.colors.green");
            ChatComponentTranslation chatComponentTranslation12 = new ChatComponentTranslation("commands.colors.aqua");
            ChatComponentTranslation chatComponentTranslation13 = new ChatComponentTranslation("commands.colors.red");
            ChatComponentTranslation chatComponentTranslation14 = new ChatComponentTranslation("commands.colors.lightPurple");
            ChatComponentTranslation chatComponentTranslation15 = new ChatComponentTranslation("commands.colors.yellow");
            ChatComponentTranslation chatComponentTranslation16 = new ChatComponentTranslation("commands.colors.white");
            
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            
            iCommandSender.addChatMessage(chatComponentTranslation);
            iCommandSender.addChatMessage(chatComponentTranslation1);
            iCommandSender.addChatMessage(chatComponentTranslation2);
            iCommandSender.addChatMessage(chatComponentTranslation3);
            iCommandSender.addChatMessage(chatComponentTranslation4);
            iCommandSender.addChatMessage(chatComponentTranslation5);
            iCommandSender.addChatMessage(chatComponentTranslation6);
            iCommandSender.addChatMessage(chatComponentTranslation7);
            iCommandSender.addChatMessage(chatComponentTranslation8);
            iCommandSender.addChatMessage(chatComponentTranslation9);
            iCommandSender.addChatMessage(chatComponentTranslation10);
            iCommandSender.addChatMessage(chatComponentTranslation11);
            iCommandSender.addChatMessage(chatComponentTranslation12);
            iCommandSender.addChatMessage(chatComponentTranslation13);
            iCommandSender.addChatMessage(chatComponentTranslation14);
            iCommandSender.addChatMessage(chatComponentTranslation15);
            iCommandSender.addChatMessage(chatComponentTranslation16);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}