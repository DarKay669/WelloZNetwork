package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.util.ModUtil;
import fr.welloznetwork.wellozmod.util.ModUtil.HomeUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class CommandSethome extends CommandBase implements ICommandPermission
{
	public String getCommandName()
	{
		return "sethome";
	}
	
	public String getCommandUsage(ICommandSender iCommandSender)
	{
		return "commands.sethome.usage";
	}
	
	public void processCommand(ICommandSender iCommandSender, String[] args)
	{
	    if (iCommandSender instanceof EntityPlayerMP)
	    {
	        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iCommandSender;
	        
	        if (args.length <= 0)
	        {
	            throw new WrongUsageException("commands.sethome.usage", new Object[0]);
	        }
	        
	        if (args.length == 1)
	        {
	            int stringMode = HomeUtils.isValidName(args[0]);
	            
	            if (stringMode > 0)
	            {
	                if (stringMode == 1)
	                {
	                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.sethome.nameTooShort");
	                    chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
	                    
	                    entityPlayerMP.addChatMessage(chatComponentTranslation);
	                } 
	                
	                else if (stringMode == 2)
	                {
	                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.sethome.startWithNumber");
	                    chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
	                    
	                    entityPlayerMP.addChatMessage(chatComponentTranslation);
	                }
	                
	                return;
	            }
	            
	            EntityPlayer entityPlayer = iCommandSender.getEntityWorld().getPlayerEntityByName(iCommandSender.getCommandSenderName());
	            
	            if (entityPlayer != null)
	            {
	                NBTTagCompound tpData = ModUtil.HomeUtils.loadTeleportLocations(entityPlayer);
	                NBTTagList nbtTagList = tpData.getTagList("home", 10);
	                
	                if (nbtTagList != null)
	                {
	                    int maxHome = this.getMaxHome(RegistryGrades.getGrade(entityPlayerMP));
	                    
	                    if (nbtTagList.tagCount() >= maxHome)
	                    {
	                        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.sethome.maxHome", maxHome);
	                        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
	                        
	                        entityPlayerMP.addChatMessage(chatComponentTranslation);
	                        return;
	                    }
	                    
	                    for (int i = 0; i < nbtTagList.tagCount(); i++) 
	                    {
	                        NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
	                        
	                        if (args[0].equalsIgnoreCase(nbtTagCompound.getString("name")) || nbtTagCompound.getString("name").equalsIgnoreCase(""))
	                        {
	                            nbtTagList.removeTag(i);
	                        }
	                    }
	                    
	                    double yaw = (entityPlayer.rotationYaw % 360.0F);
	                    
	                    if (yaw > 45.0D && yaw <= 135.0D)
	                    {
	                        yaw = 90.0D;
	                    }
	                    
	                    else if (yaw > 135.0D && yaw <= 225.0D) 
	                    {
	                        yaw = 180.0D;
	                    }
	                    
	                    else if (yaw > 255.0D && yaw <= 315.0D)
	                    {
	                        yaw = 270.0D;
	                    }
	                    
	                    else if (yaw > 315.0D)
	                    {
	                        yaw = 0.0D;
	                    }
	                    
	                    else if (yaw > -45.0D && yaw <= 45.0D)
	                    {
	                        yaw = 0.0D;
	                    } 
	                    
	                    else if (yaw > -135.0D && yaw <= -45.0D)
	                    {
	                        yaw = -90.0D;
	                    } 
	                    
	                    else if (yaw > -225.0D && yaw <= -135.0D)
	                    {
	                        yaw = -180.0D;
	                    } 
	                    
	                    else if (yaw > -351.0D && yaw <= -225.0D)
	                    {
	                        yaw = -270.0D;
	                    }
	                    
	                    else if (yaw <= -315.0D)
	                    {
	                        yaw = 0.0D;
	                    }
	                    
	                    NBTTagCompound nbtTagCompound = (new NBTTagCompound()).getCompoundTag(args[0]);
	                    nbtTagCompound.setString("name", args[0]);
	                    nbtTagCompound.setInteger("x", MathHelper.floor_double(entityPlayer.posX));
	                    nbtTagCompound.setInteger("y", MathHelper.floor_double(entityPlayer.posY));
	                    nbtTagCompound.setInteger("z", MathHelper.floor_double(entityPlayer.posZ));
	                    nbtTagCompound.setInteger("yaw", MathHelper.floor_double(yaw));
	                    nbtTagCompound.setInteger("dimension", entityPlayer.dimension);
	                    nbtTagList.appendTag((NBTBase) nbtTagCompound);
	                    
	                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.sethome.created", new ChatComponentText(EnumChatFormatting.GOLD + args[0] + EnumChatFormatting.WHITE));
	                    
	                    entityPlayerMP.addChatMessage(chatComponentTranslation);
	                    
	                    tpData.setTag("home", (NBTBase) nbtTagList);
	                    HomeUtils.saveTeleportLocations(entityPlayer, tpData);
	                }
	            }
	        }
	    }
	}
	
	public GradeType getPermissionType()
	{
	    return GradeType.PLAYER;
	}
	
	private int getMaxHome(GradeType gradeType)
	{
	    switch (gradeType)
	    {
	        case ADMINISTRATOR:
	            return 5;
	            
	        case STAFF:
	            return 5;
	            
	        case DONATOR:
	            return 5;
	            
	        case PLAYER:
	            return 2;
	            
	        case NOT_SET:
	            default:
	                return 0;
	    }
	}
}