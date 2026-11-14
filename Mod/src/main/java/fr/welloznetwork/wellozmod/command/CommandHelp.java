package fr.welloznetwork.wellozmod.command;

import java.util.List;

import com.google.common.collect.Lists;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.init.RegistryGrades;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;

@Custom(net.minecraft.command.CommandHelp.class)
public class CommandHelp extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "help";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public List getCommandAliases()
    {
        return Lists.newArrayList("?");
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender;
            GradeType gradeType = RegistryGrades.getGrade(entityPlayer.getUniqueID());
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.help.header");
            ChatComponentTranslation chatComponentTranslation31 = new ChatComponentTranslation("commands.help.clearlag");
            ChatComponentTranslation chatComponentTranslation28 = new ChatComponentTranslation("commands.help.colors");
            ChatComponentTranslation chatComponentTranslation1 = new ChatComponentTranslation("commands.help.delhome");
            ChatComponentTranslation chatComponentTranslation20 = new ChatComponentTranslation("commands.help.discord");
            ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.help.help");
            ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation("commands.help.home");
            ChatComponentTranslation chatComponentTranslation29 = new ChatComponentTranslation("commands.help.list");
            ChatComponentTranslation chatComponentTranslation30 = new ChatComponentTranslation("commands.help.lobby");
            ChatComponentTranslation chatComponentTranslation4 = new ChatComponentTranslation("commands.help.money");
            ChatComponentTranslation chatComponentTranslation5 = new ChatComponentTranslation("commands.help.pay");
            ChatComponentTranslation chatComponentTranslation6 = new ChatComponentTranslation("commands.help.rtp");
            ChatComponentTranslation chatComponentTranslation7 = new ChatComponentTranslation("commands.help.sethome");
            ChatComponentTranslation chatComponentTranslation23 = new ChatComponentTranslation("commands.help.spawn");
            ChatComponentTranslation chatComponentTranlation24 = new ChatComponentTranslation("commands.help.tpa");
            ChatComponentTranslation chatComponentTranlation25 = new ChatComponentTranslation("commands.help.tpAccept");
            ChatComponentTranslation chatComponentTranlation26 = new ChatComponentTranslation("commands.help.tpDeny");
            ChatComponentTranslation chatComponentTranslation8 = new ChatComponentTranslation("commands.help.footer");
            
            iCommandSender.addChatMessage(chatComponentTranslation);
            
            if (Constants.V2)
            {
                iCommandSender.addChatMessage(chatComponentTranslation28);
            }
            
            iCommandSender.addChatMessage(chatComponentTranslation31);
            iCommandSender.addChatMessage(chatComponentTranslation1);
            iCommandSender.addChatMessage(chatComponentTranslation20);
            iCommandSender.addChatMessage(chatComponentTranslation2);
            iCommandSender.addChatMessage(chatComponentTranslation3);
            iCommandSender.addChatMessage(chatComponentTranslation29);
            iCommandSender.addChatMessage(chatComponentTranslation30);
            iCommandSender.addChatMessage(chatComponentTranslation4);
            iCommandSender.addChatMessage(chatComponentTranslation5);
            iCommandSender.addChatMessage(chatComponentTranslation6);
            iCommandSender.addChatMessage(chatComponentTranslation7);
            iCommandSender.addChatMessage(chatComponentTranslation23);
            iCommandSender.addChatMessage(chatComponentTranlation24);
            iCommandSender.addChatMessage(chatComponentTranlation25);
            iCommandSender.addChatMessage(chatComponentTranlation26);
            
            if (gradeType.ordinal() >= GradeType.DONATOR.ordinal())
            {
                ChatComponentTranslation chatComponentTranslation9 = new ChatComponentTranslation("commands.help.craft");
                ChatComponentTranslation chatComponentTranslation21 = new ChatComponentTranslation("commands.help.deathLocate");
                ChatComponentTranslation chatComponentTranslation10 = new ChatComponentTranslation("commands.help.enderchest");
                ChatComponentTranslation chatComponentTranslation11 = new ChatComponentTranslation("commands.help.feed");
                ChatComponentTranslation chatComponentTranslation12 = new ChatComponentTranslation("commands.help.furnace");
                ChatComponentTranslation chatComponentTranslation13 = new ChatComponentTranslation("commands.help.heal");
                ChatComponentTranslation chatComponentTranslation22 = new ChatComponentTranslation("commands.help.nightVision");
                ChatComponentTranslation chatComponentTranslation14 = new ChatComponentTranslation("commands.help.repair");
                ChatComponentTranslation chatComponentTranslation27 = new ChatComponentTranslation("commands.help.tpaHere");
                
                if (Constants.V2)
                {
                    iCommandSender.addChatMessage(chatComponentTranslation9);
                    iCommandSender.addChatMessage(chatComponentTranslation21);
                    iCommandSender.addChatMessage(chatComponentTranslation10);
                    iCommandSender.addChatMessage(chatComponentTranslation22);
                }
                
                iCommandSender.addChatMessage(chatComponentTranslation11);
                iCommandSender.addChatMessage(chatComponentTranslation12);
                iCommandSender.addChatMessage(chatComponentTranslation13);
                iCommandSender.addChatMessage(chatComponentTranslation14);
                iCommandSender.addChatMessage(chatComponentTranslation27);
            }
            
            if (gradeType.ordinal() >= GradeType.STAFF.ordinal())
            {
                ChatComponentTranslation chatComponentTranslation32 = new ChatComponentTranslation("commands.help.clearlagstaff");
                ChatComponentTranslation chatComponentTranslation15 = new ChatComponentTranslation("commands.help.invsee");
                ChatComponentTranslation chatComponentTranslation16 = new ChatComponentTranslation("commands.help.nickname");
                ChatComponentTranslation chatComponentTranslation19 = new ChatComponentTranslation("commands.help.notification");
                ChatComponentTranslation chatComponentTranslation17 = new ChatComponentTranslation("commands.help.playerhead");
                ChatComponentTranslation chatComponentTranslation18 = new ChatComponentTranslation("commands.help.spectator");
                
                iCommandSender.addChatMessage(chatComponentTranslation32);
                iCommandSender.addChatMessage(chatComponentTranslation15);
                iCommandSender.addChatMessage(chatComponentTranslation16);
                iCommandSender.addChatMessage(chatComponentTranslation19);
                iCommandSender.addChatMessage(chatComponentTranslation17);
                iCommandSender.addChatMessage(chatComponentTranslation18);
            }
            
            if (gradeType.ordinal() >= GradeType.ADMINISTRATOR.ordinal())
            {
                ChatComponentTranslation chatComponentTranslation19 = new ChatComponentTranslation("commands.help.grade");
                ChatComponentTranslation chatComponentTranslation24 = new ChatComponentTranslation("commands.help.setSpawn");
                
                iCommandSender.addChatMessage(chatComponentTranslation19);
                iCommandSender.addChatMessage(chatComponentTranslation24);
            }
            
            iCommandSender.addChatMessage(chatComponentTranslation8);
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}