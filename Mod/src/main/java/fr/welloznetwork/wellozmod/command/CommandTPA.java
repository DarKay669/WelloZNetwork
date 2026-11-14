package fr.welloznetwork.wellozmod.command;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import cpw.mods.fml.common.FMLCommonHandler;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandTPA extends CommandBase implements ICommandPermission
{
    public static final HashMap<Teleport, Integer> PLAYERS_MAP = new HashMap<>();
    
    public String getCommandName()
    {
        return "tpa";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.tpa.usage";
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            if (args.length != 1)
            {
                throw new WrongUsageException("commands.tpa.usage");
            }
            
            EntityPlayerMP entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().func_152612_a(args[0]);
            
            if (entityPlayerMP == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.playerNotFound", new ChatComponentText(args[0]));
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
                
                return;
            }
            
            Teleport remove = getReceiverTeleport(iCommandSender.getCommandSenderName());
            
            if (remove != null)
            {
                PLAYERS_MAP.remove(remove);
                notifyCanceledTeleport(remove);
            }
            
            ToTeleport toTeleport = new ToTeleport(iCommandSender.getCommandSenderName(), args[0]);
            
            PLAYERS_MAP.put(toTeleport, 1200);
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpa.hasRequested", args[0]);
            ChatComponentTranslation chatComponentTranslation1 = new ChatComponentTranslation("commands.tpa.hasRequested1");
            ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.tpa.hasRequested2", (1200 / 20));
            ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation("commands.tpa.requested", args[0]);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GOLD);
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            
            iCommandSender.addChatMessage(chatComponentTranslation3);
            
            entityPlayerMP.addChatMessage(chatComponentTranslation);
            entityPlayerMP.addChatMessage(chatComponentTranslation1);
            entityPlayerMP.addChatMessage(chatComponentTranslation2);
        }
    }
    
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
    
    @Nullable
    public static Teleport getSenderTeleport(String playerName)
    {
        for (Teleport teleport : PLAYERS_MAP.keySet())
        {
            if (teleport.getSender().equalsIgnoreCase(playerName))
            {
                return teleport;
            }
        }
        
        return null;
    }
    
    @Nullable
    public static Teleport getReceiverTeleport(String playerName)
    {
        for (Teleport teleport : PLAYERS_MAP.keySet())
        {
            if (teleport.getReceiver().equalsIgnoreCase(playerName))
            {
                return teleport;
            }
        }
        
        return null;
    }
    
    public static void notifyCanceledTeleport(Teleport teleport)
    {
        EntityPlayerMP entityPlayerMP = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152612_a(teleport.getSender());
        EntityPlayerMP entityPlayerMP1 = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152612_a(teleport.getReceiver());
        
        if (entityPlayerMP != null)
        {
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpa.teleportCancelled", teleport.getSender());
            
            entityPlayerMP.addChatMessage(chatComponentTranslation);
        }
        
        if (entityPlayerMP1 != null)
        {
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.tpa.cancelled", teleport.getReceiver());
            
            entityPlayerMP1.addChatMessage(chatComponentTranslation);
        }
    }
    
    public static abstract class Teleport
    {
        public final TeleportType teleportType;
        
        public Teleport(TeleportType teleportType)
        {
            this.teleportType = teleportType;
        }
        
        public abstract String getSender();
        
        public abstract String getReceiver();
    }
    
    public class ToTeleport extends Teleport
    {
        private final String requester, destination;
        
        public ToTeleport(String requester, String destination)
        {
            super(TeleportType.TELEPORT_TO);
            this.requester = requester;
            this.destination = destination;
        }
        
        public String getSender()
        {
            return this.requester;
        }
        
        public String getDestination()
        {
            return this.destination;
        }
        
        public String getReceiver()
        {
            return this.destination;
        }
    }
    
    public static class HereTeleport extends Teleport
    {
        private final String sender, receiver;
        
        public HereTeleport(String sender, String receiver)
        {
            super(TeleportType.TELEPORT_TO);
            this.sender = sender;
            this.receiver = receiver;
        }
        
        public String getSender()
        {
            return this.sender;
        }
        
        public String getDestination()
        {
            return this.receiver;
        }
        
        public String getReceiver()
        {
            return this.receiver;
        }
    }
    
    public enum TeleportType
    {
        TELEPORT_TO, TELEPORT_BRING;
    }
}