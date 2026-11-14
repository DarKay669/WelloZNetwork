package fr.welloznetwork.wellozmod.command;

import java.util.HashMap;
import java.util.Map;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.util.ModUtil.HomeUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandHome extends CommandBase implements ICommandPermission
{
    private static final Map<String, Long> COOLDOWN_MAP = new HashMap<>();
    
	public String getCommandName() 
	{
		return "home";
	}
	
	public String getCommandUsage(ICommandSender iCommandSender)
	{
		return "commands.home.usage";
	}
	
	public void processCommand(ICommandSender iCommandSender, String[] args)
	{
	    if (iCommandSender instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iCommandSender;
            
            if (args.length <= 1)
            {
                EntityPlayer entityPlayer = iCommandSender.getEntityWorld().getPlayerEntityByName(iCommandSender.getCommandSenderName());
                
                if (entityPlayer != null)
                {
                    NBTTagCompound nbtTagCompound = HomeUtils.loadTeleportLocations(entityPlayer);
                    NBTTagList nbtTagList = nbtTagCompound.getTagList("home", 10);
                    
                    if (nbtTagList != null)
                    {                   
                        if (args.length <= 0)
                        {
                            String homeList = "";
                            
                            for (int i = 0; i < nbtTagList.tagCount(); i++)
                            {
                                NBTTagCompound tpTag = nbtTagList.getCompoundTagAt(i);
                                
                                homeList = homeList + tpTag.getString("name") + ", ";
                            }
                            
                            if (homeList.equalsIgnoreCase(""))
                            {
                                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.home.noHomeFound");
                                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                                
                                entityPlayer.addChatMessage(chatComponentTranslation);
                            }
                            
                            else
                            {
                                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.home.list", new ChatComponentText("" + EnumChatFormatting.GOLD + homeList));
                                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.WHITE);
                                
                                entityPlayer.addChatMessage(chatComponentTranslation);
                            }
                            
                            return;
                        }
                        
                        else
                        {
                            if (COOLDOWN_MAP.containsKey(entityPlayer.getCommandSenderName()))
                            {
                                long timeLeft = 10000L - (System.currentTimeMillis() - COOLDOWN_MAP.get(entityPlayer.getCommandSenderName()));
                                
                                if (timeLeft > 0)
                                {
                                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.rtp.wait", new ChatComponentText(String.valueOf(timeLeft / 1000L)), new ChatComponentTranslation("commands.rtp.wait1"));
                                    chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                                    
                                    entityPlayer.addChatMessage(chatComponentTranslation);
                                    return;
                                }
                            }
                        }
                        
                        if (nbtTagList.tagCount() <= 0)
                        {
                            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.home.noHomeFound");
                            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                            
                            entityPlayerMP.addChatMessage(chatComponentTranslation);
                        }
                        
                        else if (nbtTagList.tagCount() >= 1)
                        {
                            NBTTagCompound tpTagTo = null;
                            
                            for (int i = 0; i < nbtTagList.tagCount(); i++)
                            {
                                NBTTagCompound tpTag = nbtTagList.getCompoundTagAt(i);
                                
                                if (args[0].equalsIgnoreCase(tpTag.getString("name")))
                                {
                                    tpTagTo = tpTag;
                                    break;
                                }
                            }
                            
                            if (tpTagTo != null)
                            {
                                int dimension = 0;
                                
                                if (tpTagTo.hasKey("dimension"))
                                {
                                    dimension = tpTagTo.getInteger("dimension");
                                }
                                
                                if (entityPlayer.dimension != 1 || dimension != 1);
                                
                                if (entityPlayer.dimension == dimension)
                                {
                                    entityPlayer.rotationPitch = 0.0F;
                                    entityPlayer.rotationYaw = tpTagTo.getInteger("yaw");
                                    entityPlayer.setPositionAndUpdate(tpTagTo.getInteger("x") + 0.5D, tpTagTo.getInteger("y"), tpTagTo.getInteger("z") + 0.5D);
                                    
                                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.home.teleported", new ChatComponentText(EnumChatFormatting.GOLD + tpTagTo.getString("name")));
                                    
                                    entityPlayerMP.addChatMessage(chatComponentTranslation);
                                    
                                    COOLDOWN_MAP.put(entityPlayer.getCommandSenderName(), System.currentTimeMillis());
                                }
                                
                                else 
                                {
                                    HomeUtils.transferPlayerToDimension((EntityPlayerMP) entityPlayer, tpTagTo.getInteger("x"), tpTagTo.getInteger("y"), tpTagTo.getInteger("z"), tpTagTo.getInteger("yaw"), dimension);
                                    
                                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.home.teleported", new ChatComponentText(EnumChatFormatting.GOLD + tpTagTo.getString("name")));
                                    
                                    entityPlayerMP.addChatMessage(chatComponentTranslation);
                                    
                                    COOLDOWN_MAP.put(entityPlayer.getCommandSenderName(), System.currentTimeMillis());
                                }
                            }
                            
                            else
                            {
                                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.delhome.notFound", new ChatComponentText(args[0]));
                                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                                
                                entityPlayerMP.addChatMessage(chatComponentTranslation);
                            }
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