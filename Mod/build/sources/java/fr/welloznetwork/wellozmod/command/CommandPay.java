package fr.welloznetwork.wellozmod.command;

import java.util.List;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.entity.player.CustomEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import fr.welloznetwork.wellozmod.network.play.server.S03PacketMoney;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandPay extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "pay";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.pay.usage";
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayer)
        {
            if (args.length < 2)
            {
                throw new WrongUsageException("commands.pay.usage", new Object[0]);
            }
            
            int amount;
            
            try
            {
                amount = Integer.parseInt(args[0]);
                
                if (amount <= 0)
                {
                    throw new NumberFormatException();
                }
            }
            
            catch (NumberFormatException e)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.invalidAmount");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
                return;
            }
            
            EntityPlayer entityPlayer = (EntityPlayer) iCommandSender;
            EntityPlayer entityPlayer1 = entityPlayer.worldObj.getPlayerEntityByName(args[1]);
            
            if (entityPlayer1 == null)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.playerNotFound", new ChatComponentText(args[1]));
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
                return;
            }
            
            CustomEntityPlayer customEntityPlayer = CustomEntityPlayer.get(entityPlayer);
            CustomEntityPlayer customEntityPlayer1 = CustomEntityPlayer.get(entityPlayer1);
            
            if (customEntityPlayer.getMoney() < amount)
            {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.notEnoughMoney");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                
                iCommandSender.addChatMessage(chatComponentTranslation);
                return;
            }
            
            customEntityPlayer.decreaseMoney(amount);
            customEntityPlayer1.addMoney(amount);
            
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.pay.sent", new ChatComponentText("" + EnumChatFormatting.GREEN + amount + "$"), new ChatComponentText(entityPlayer1.getCommandSenderName()));
            ChatComponentTranslation chatComponentTranslation1 = new ChatComponentTranslation("commands.pay.received", new ChatComponentText(entityPlayer.getCommandSenderName()), new ChatComponentText("" + EnumChatFormatting.GREEN + amount + "$"));
            
            iCommandSender.addChatMessage(chatComponentTranslation);
            entityPlayer1.addChatMessage(chatComponentTranslation1);
            
            WelloZMod.proxy.getNetworkManager().sendToPlayer(new S03PacketMoney(customEntityPlayer.getMoney()), (EntityPlayerMP) entityPlayer);
            WelloZMod.proxy.getNetworkManager().sendToPlayer(new S03PacketMoney(customEntityPlayer1.getMoney()), (EntityPlayerMP) entityPlayer1);
        }
    }
    
    public List addTabCompletionOptions(ICommandSender iCommandSender, String[] args)
    {
        return args.length == 2 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 1;
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.PLAYER;
    }
}