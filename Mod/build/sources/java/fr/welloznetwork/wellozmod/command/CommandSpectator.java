package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.network.play.client.C06PacketSpectator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class CommandSpectator extends CommandBase implements ICommandPermission
{
    private static boolean isSpectator = false;
    
    public String getCommandName()
    {
        return "spectator";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("gm3", "sp");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iCommandSender;
            
            entityPlayerMP.noClip = !entityPlayerMP.noClip;
            entityPlayerMP.capabilities.isFlying = true;
            entityPlayerMP.capabilities.allowFlying = true;
            entityPlayerMP.sendPlayerAbilities();
            
            WelloZMod.proxy.getNetworkManager().sendToPlayer(new C06PacketSpectator(entityPlayerMP.noClip), entityPlayerMP);
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("gameMode.changed");
            
            iCommandSender.addChatMessage(chatComponentTranslation);
            
            isSpectator = !isSpectator;
        }
    }
    
    public static boolean isSpectator()
    {
        return isSpectator;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.STAFF;
    }
}