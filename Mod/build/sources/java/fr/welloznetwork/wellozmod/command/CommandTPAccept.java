package fr.welloznetwork.wellozmod.command;

import java.util.HashMap;
import java.util.Map;

import fr.welloznetwork.wellozmod.command.CommandSpawn.TeleportData;
import fr.welloznetwork.wellozmod.command.CommandTPA.Teleport;
import fr.welloznetwork.wellozmod.command.CommandTPA.ToTeleport;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandTPAccept extends CommandBase implements ICommandPermission
{
    public static final Map<EntityPlayerMP, TeleportData> COOLDOWN_MAP = new HashMap<>();
    
    public String getCommandName()
    {
        return "tpaccept";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            int cooldown;
            
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
            EntityPlayerMP receiver = MinecraftServer.getServer().getConfigurationManager().func_152612_a(teleport.getReceiver());
            
            if (receiver == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.playerNotFound", new ChatComponentText(args[0]));
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
                
                return;
            }
            
            GradeType gradeType = RegistryGrades.getGrade((EntityPlayerMP) iCommandSender);
            
            if (gradeType.ordinal() >= gradeType.DONATOR.ordinal())
            {
                cooldown = 0;
            }
            
            else
            {
                cooldown = 60;
            }
            
            int[] data = new int[5];
            
            data[0] = (int) sender.posX;
            data[1] = (int) sender.posY;
            data[2] = (int) sender.posZ;
            data[3] = (int) sender.rotationYaw;
            data[4] = sender.dimension;
            
            COOLDOWN_MAP.put(sender, new TeleportData(cooldown, sender, data));
            
            if (teleport instanceof ToTeleport)
            {
                EntityPlayerMP entityPlayerMP = receiver;
                
                receiver = sender;
                sender = entityPlayerMP;
            }
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.spawn.teleport", (cooldown / 20));
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY);
            
            ChatComponentTranslation chatComponentTranslation1 = new ChatComponentTranslation("commands.tpAccept.accept", sender.getCommandSenderName(), (cooldown / 20));
            chatComponentTranslation1.getChatStyle().setColor(EnumChatFormatting.GOLD);
            
            if (gradeType.ordinal() < gradeType.DONATOR.ordinal())
            {
                sender.addChatMessage(chatComponentTranslation);
            }
            
            receiver.addChatMessage(chatComponentTranslation1);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}