package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandPlayerHead extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "playerhead";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.playerhead.usage";
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("head");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            if (args.length <= 0)
            {
                throw new WrongUsageException("commands.playerhead.usage", new Object[0]);
            }
            
            EntityPlayer entityPlayer = (EntityPlayer) getPlayer((EntityPlayer) iCommandSender, args[0]);        
            ItemStack head = new ItemStack(Items.skull, 1, 3);
            head.setTagCompound(new NBTTagCompound());
            head.getTagCompound().setString("SkullOwner", entityPlayer.getDisplayName());
            entityPlayer.getEntityWorld().spawnEntityInWorld(new EntityItem(entityPlayer.getEntityWorld(), entityPlayer.getPlayerCoordinates().posX, entityPlayer.getPlayerCoordinates().posY + 1, entityPlayer.getPlayerCoordinates().posZ, head));
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.playerhead.obtain", new ChatComponentText(entityPlayer.getDisplayName()));
            
            iCommandSender.addChatMessage(chatComponentTranslation);
        }
    }
    
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args)
    {
        return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
    }
    
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.STAFF;
    }
}