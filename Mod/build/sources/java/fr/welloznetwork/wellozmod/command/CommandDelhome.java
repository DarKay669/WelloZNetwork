package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.util.ModUtil.HomeUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandDelhome extends CommandBase implements ICommandPermission
{
	public String getCommandName()
	{
		return "delhome";
	}
	
	public String getCommandUsage(ICommandSender iCommandSender)
	{
		return "commands.delhome.usage";
	}
	
	public void processCommand(ICommandSender iCommandSender, String[] args)
	{
	    if (iCommandSender instanceof EntityPlayer)
        {
	        EntityPlayer entityPlayer = (EntityPlayer) iCommandSender;
            
            if (args.length <= 0)
            {
                throw new WrongUsageException("commands.delhome.usage", new Object[0]);
            }
            
            if (args.length == 1)
            {
                EntityPlayer entityPlayer1 = iCommandSender.getEntityWorld().getPlayerEntityByName(iCommandSender.getCommandSenderName());

                if (entityPlayer != null)
                {
                    NBTTagCompound nbtTagCompound = HomeUtils.loadTeleportLocations(entityPlayer);
                    NBTTagList nbtTagList = nbtTagCompound.getTagList("home", 10);
                    
                    if (nbtTagList != null)
                    {
                        boolean deleted = false;
                        
                        for (int i = 0; i < nbtTagList.tagCount(); i++)
                        {
                            NBTTagCompound tpTag = nbtTagList.getCompoundTagAt(i);
                            
                            if (args[0].equalsIgnoreCase(tpTag.getString("name")))
                            {
                                nbtTagList.removeTag(i);
                                deleted = true;
                            }
                        }
                        
                        if (deleted)
                        {
                            nbtTagCompound.setTag("home", (NBTBase) nbtTagList);
                            
                            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.delhome.removed", new ChatComponentText(EnumChatFormatting.GOLD + args[0]));
                            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.WHITE);
                            
                            entityPlayer1.addChatMessage(chatComponentTranslation);
                            HomeUtils.saveTeleportLocations(entityPlayer1, nbtTagCompound);
                        }
                        
                        else
                        {
                            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.delhome.notFound", new ChatComponentText(EnumChatFormatting.GOLD + args[0]));
                            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                            
                            entityPlayer1.addChatMessage(chatComponentTranslation);
                        }
                    }
                }
            }
        }
	}
	
	public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}