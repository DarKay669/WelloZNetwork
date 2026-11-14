package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandFurnace extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "furnace";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }

    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender;
            ItemStack itemStack = entityPlayer.getHeldItem();
            
            if (itemStack == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.furnace.itemInHand");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayer.addChatMessage(chatComponentTranslation);
                
                return;
            }
            
            ItemStack itemStack1 = FurnaceRecipes.smelting().getSmeltingResult(itemStack);
            
            if (itemStack1 == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.furnace.cantSmelted");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayer.addChatMessage(chatComponentTranslation);
                
                return;
            }
            
            itemStack1.stackSize = (entityPlayer.inventory.getStackInSlot(entityPlayer.inventory.currentItem)).stackSize;
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            entityPlayer.inventory.addItemStackToInventory(itemStack1);
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.furnace.smelted");
            
            entityPlayer.addChatMessage(chatComponentTranslation);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
}