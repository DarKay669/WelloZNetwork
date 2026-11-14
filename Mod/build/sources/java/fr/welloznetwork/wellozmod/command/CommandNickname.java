package fr.welloznetwork.wellozmod.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.network.play.client.C03PacketNickname;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandNickname extends CommandBase implements ICommandPermission
{
    public static final Map<UUID, String> NICKNAMES = new HashMap<>();
    
    public String getCommandName()
    {
        return "nickname";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.nickname.usage";
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("nick");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (!(iCommandSender instanceof EntityPlayerMP))
        {
            return;
        }
        
        if (args.length < 1)
        {
            throw new WrongUsageException("commands.nickname.usage", new Object[0]);
        }
        
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iCommandSender;
        String nickname = String.join(" ", args);
        
        if (nickname.length() < 3 || nickname.length() > 16)
        {
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.nickname.length");
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            
            iCommandSender.addChatMessage(chatComponentTranslation);
            return;
        }
        
        setNickname(entityPlayerMP, nickname);
        NICKNAMES.put(entityPlayerMP.getUniqueID(), nickname);
        
        WelloZMod.proxy.getNetworkManager().sendToServer(new C03PacketNickname(entityPlayerMP.getUniqueID(), nickname));
        WelloZMod.proxy.getNetworkManager().sendToAll(new C03PacketNickname(entityPlayerMP.getUniqueID(), nickname));
        
        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.nickname.newNickname", new ChatComponentText(nickname));
        
        entityPlayerMP.addChatMessage(chatComponentTranslation);
    }
    
    public static void setNickname(EntityPlayer entityPlayer, String nickname)
    {
        NICKNAMES.put(entityPlayer.getUniqueID(), nickname);
    }
    
    public static String getNickname(EntityPlayer entityPlayer)
    {
        return NICKNAMES.getOrDefault(entityPlayer.getUniqueID(), entityPlayer.getCommandSenderName());
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.STAFF;
    }
}