package fr.welloznetwork.wellozmod.command;

import java.util.HashMap;
import java.util.Map;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;

public class CommandDeathLocate extends CommandBase implements ICommandPermission
{
    private static final Map<String, ChunkCoordinates> LAST_DEATH = new HashMap<>();
    
    public String getCommandName()
    {
        return "deathlocate";
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
            ChunkCoordinates chunkCoordinates = LAST_DEATH.get(entityPlayer.getCommandSenderName());
            
            if (chunkCoordinates == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.deathLocate.noLocation");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                entityPlayer.addChatMessage(chatComponentTranslation);
            }
            
            else
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.deathLocate.location", chunkCoordinates.posX, chunkCoordinates.posY, chunkCoordinates.posZ);
                
                entityPlayer.addChatMessage(chatComponentTranslation);
            }
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
    
    public static void onLivingDeathEvent(EntityPlayer entityPlayer)
    {
        LAST_DEATH.put(entityPlayer.getCommandSenderName(), new ChunkCoordinates((int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ));
    }
}