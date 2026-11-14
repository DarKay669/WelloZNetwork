package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandRepair extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "repair";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("fix");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender;
            
            ChatComponentTranslation chatComponentTranslation4 = new ChatComponentTranslation("commands.repair.itemFixed");
            chatComponentTranslation4.getChatStyle().setColor(EnumChatFormatting.WHITE);
            
            if (entityPlayer.getHeldItem() == null || entityPlayer.getHeldItem().getItem() == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.repair.itemInHand");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayer.addChatMessage(chatComponentTranslation);
            }
            
            else if (entityPlayer.getHeldItem().getItem() != null && entityPlayer.getHeldItem().getItemDamage() < 0)
            {
                chatComponentTranslation4.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayer.addChatMessage(chatComponentTranslation4);
            }
            
            else if (entityPlayer.getHeldItem().getItem() instanceof ItemBlock)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.repair.block");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayer.addChatMessage(chatComponentTranslation);
            }
            
            else
            {
                ItemStack itemStack = entityPlayer.getHeldItem(), slot = null;
                int slotAmount = 0;
                
                if (itemStack.getItemDamage() > 0)
                {
                    slotAmount++;
                    slot = itemStack;
                }
                
                if (slotAmount == 1)
                {
                    slot.damageItem(-100, entityPlayer);
                }
                
                else if (slotAmount == 2)
                {
                    int random = entityPlayer.worldObj.rand.nextInt(slotAmount);
                    
                    if (random == 0)
                    {
                        slot.damageItem(-100, entityPlayer);
                    }
                }
                
                else if (slotAmount == 3)
                {
                    int random = entityPlayer.worldObj.rand.nextInt(slotAmount);
                    
                    if (random == 0)
                    {
                        slot.damageItem(-100, entityPlayer);
                    }
                }
                
                else if (slotAmount == 4)
                {
                    int random = entityPlayer.worldObj.rand.nextInt(slotAmount);
                    
                    if (random == 0)
                    {
                        slot.damageItem(-100, entityPlayer);
                    }
                }
                
                else if (slotAmount == 5)
                {
                    int random = entityPlayer.worldObj.rand.nextInt(slotAmount);
                    
                    if (random == 0)
                    {
                        slot.damageItem(-100, entityPlayer);
                    }
                }
                
                entityPlayer.addChatMessage(chatComponentTranslation4);
            }
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
}