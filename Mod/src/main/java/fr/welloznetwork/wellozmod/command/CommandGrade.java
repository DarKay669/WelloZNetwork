package fr.welloznetwork.wellozmod.command;

import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.network.play.server.S07PacketGrades;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandGrade extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "grade";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.grade.usage";
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (args.length < 2)
        {
            throw new WrongUsageException("commands.grade.usage", new Object[0]);
        }
        
        GradeType gradeType = this.getGradeFromCommand(iCommandSender, args[0]);
        
        if (gradeType == null || gradeType == GradeType.NOT_SET)
        {
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.grade.wrongGrade", new ChatComponentText(args[0].toLowerCase()));
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            
            iCommandSender.addChatMessage(chatComponentTranslation);
            return;
        }
        
        UUID uuid;
        EntityPlayerMP entityPlayerMP = getPlayer(iCommandSender, args[1]);
        
        if (entityPlayerMP != null)
        {
            uuid = entityPlayerMP.getUniqueID();
            
            ChatComponentTranslation chatComponentText = new ChatComponentTranslation(gradeType.getTranslationKey());
            chatComponentText.getChatStyle().setColor(gradeType.getColor());
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.grade.self", chatComponentText);
            
            entityPlayerMP.addChatMessage(chatComponentTranslation);
        }
        
        else
        {
            GameProfile gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(args[1]);
            
            if (gameProfile == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.playerNotFound", new ChatComponentText(args[1]));
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
                return;
            }
            
            uuid = gameProfile.getId();
        }
        
        RegistryGrades.setGrade(uuid, gradeType);
        
        WelloZMod.proxy.getNetworkManager().sendToAll(new S07PacketGrades(uuid, gradeType));
        
        ChatComponentTranslation chatComponentText = new ChatComponentTranslation(gradeType.getTranslationKey());
        chatComponentText.getChatStyle().setColor(gradeType.getColor());
        
        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.grade.other", new ChatComponentText(args[1]), chatComponentText);
        
        iCommandSender.addChatMessage(chatComponentTranslation);
    }
    
    private GradeType getGradeFromCommand(ICommandSender iCommandSender, String args)
    {
        return !args.equalsIgnoreCase(GradeType.PLAYER.getName()) ? (!args.equalsIgnoreCase(GradeType.DONATOR.getName()) ? (!args.equalsIgnoreCase(GradeType.STAFF.getName()) ? (!args.equalsIgnoreCase(GradeType.ADMINISTRATOR.getName()) ? GradeType.getByID(parseIntBounded(iCommandSender, args, 0, GradeType.values().length - 2)) : GradeType.ADMINISTRATOR) : GradeType.STAFF) : GradeType.DONATOR) : GradeType.PLAYER;
    }
    
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"player", "donator", "staff", "administrator"}): (args.length == 2 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null);
    }
    
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 1;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.ADMINISTRATOR;
    }
}