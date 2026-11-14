package fr.welloznetwork.wellozmod.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class CommandRandomTeleport extends CommandBase implements ICommandPermission
{
    private static final Map<String, Long> COOLDOWN_MAP = new HashMap<>();
    private static final long COOLDOWN = 900000L;
    
    public String getCommandName()
    {
        return "randomteleport";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("rtp");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender;
            
            if (COOLDOWN_MAP.containsKey(entityPlayer.getCommandSenderName()))
            {
                long timeLeft = COOLDOWN - (System.currentTimeMillis() - COOLDOWN_MAP.get(entityPlayer.getCommandSenderName()));
                
                if (timeLeft > 0)
                {
                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.rtp.wait", new ChatComponentText(String.valueOf(timeLeft / 60000L)), new ChatComponentTranslation("commands.rtp.wait1"));
                    chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                    
                    entityPlayer.addChatMessage(chatComponentTranslation);
                    return;
                }
            }
            
            World world = entityPlayer.worldObj;
            Random random = new Random();
            
            int x, y, z, attempts = 0;
            boolean isSafe = false;
            
            while (!isSafe && attempts < 100)
            {
                attempts++;
                
                x = entityPlayer.chunkCoordX + random.nextInt(10000) - 1000;
                z = entityPlayer.chunkCoordZ + random.nextInt(10000) - 1000;
                y = world.getHeightValue(x, z);
                
                for (int y1 = y; y1 < 256 - 2; y1++)
                {
                    Block block = world.getBlock(x, y1 - 1, z);
                    Block block1 = world.getBlock(x, y1, z);
                    Block block2 = world.getBlock(x, y1 + 1, z);
                    
                    if (block.isOpaqueCube() && block1.isAir(world, x, y1, z) && block2.isAir(world, x, y1 + 1, z))
                    {
                        entityPlayer.fallDistance = 0.0F;
                        entityPlayer.setPositionAndUpdate(x + 0.5D, y1, z + 0.5D);
                        
                        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.rtp.teleported", new ChatComponentText(String.valueOf(x) + ", " + String.valueOf(y) + ", " + String.valueOf(z)));
                        
                        entityPlayer.addChatMessage(chatComponentTranslation);
                        
                        COOLDOWN_MAP.put(entityPlayer.getCommandSenderName(), System.currentTimeMillis());
                        isSafe = true;
                        break;
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