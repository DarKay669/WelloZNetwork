package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandEnderChest extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "enderchest";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.enderchest.usage";
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("ec");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            if (args.length <= 0)
            {
                if (iCommandSender instanceof EntityPlayerMP)
                {
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iCommandSender;
                    
                    entityPlayerMP.getInventoryEnderChest().openInventory();
                    entityPlayerMP.getNextWindowId();
                    entityPlayerMP.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(entityPlayerMP.currentWindowId, 0, I18n.format("container.enderchest"), entityPlayerMP.getInventoryEnderChest().getSizeInventory(), true));
                    entityPlayerMP.openContainer = new ContainerChest(entityPlayerMP.inventory, entityPlayerMP.getInventoryEnderChest());
                    entityPlayerMP.openContainer.windowId = entityPlayerMP.currentWindowId;
                    entityPlayerMP.openContainer.addCraftingToCrafters(entityPlayerMP);
                }
                
                return;
            }
            
            EntityPlayer entityPlayer = (EntityPlayer) this.getInventoryPlayer(iCommandSender, args[0]);
            GradeType gradeType = RegistryGrades.getGrade(entityPlayer.getUniqueID());
            
            if (entityPlayer != null)
            {
                if (gradeType.ordinal() >= gradeType.STAFF.ordinal())
                {
                    for (int slotID = 0; slotID < entityPlayer.getInventoryEnderChest().getSizeInventory(); slotID++)
                    {
                        ItemStack itemStack = entityPlayer.getInventoryEnderChest().getStackInSlot(slotID);
                        
                        ChatComponentTranslation chatComponentTranslation1 = new ChatComponentTranslation("Slot " + slotID + ": " + EnumChatFormatting.GOLD + itemStack + " ");
                        
                        iCommandSender.addChatMessage(chatComponentTranslation1);
                    }
                }
                
                else
                {
                    throw new WrongUsageException("commands.generic.permission");
                }
            }
        }
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
    
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args)
    {
        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
}