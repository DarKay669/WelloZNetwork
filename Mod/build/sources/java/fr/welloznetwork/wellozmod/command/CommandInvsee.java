package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandInvsee extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "invsee";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.invsee.usage";
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("inventory", "inv");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (args.length <= 0)
        {
            throw new WrongUsageException("commands.invsee.usage", new Object[0]);
        }
        
        EntityPlayer entityPlayer = (EntityPlayer) this.getInventoryPlayer(iCommandSender, args[0]);
        
        if (entityPlayer != null)
        {
            for (int slotID = 0; slotID < entityPlayer.inventory.getSizeInventory(); slotID++)
            {
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(slotID);
                
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("Slot " + slotID + ": " + EnumChatFormatting.GOLD + itemStack + " ");
                
                iCommandSender.addChatMessage(chatComponentTranslation);
            }
        }
    }
    
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args)
    {
        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }
    
    private EntityPlayerMP getInventoryPlayer(ICommandSender iCommandSender, String playerName)
    {
        MinecraftServer minecraftServer = MinecraftServer.getServer();
        EntityPlayerMP entityPlayerMP = minecraftServer.getConfigurationManager().func_152612_a(playerName);
        
        if (entityPlayerMP == null)
        {
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.playerNotFound", new ChatComponentText(playerName));
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            
            iCommandSender.addChatMessage(chatComponentTranslation);
        }
        
        return entityPlayerMP;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.STAFF;
    }
}