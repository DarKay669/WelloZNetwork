package fr.welloznetwork.wellozmod.command;

import fr.welloznetwork.wellozmod.init.RegistryGrades.GradeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandCraft extends CommandBase implements ICommandPermission
{
    public String getCommandName()
    {
        return "craft";
    }
    
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    public void processCommand(ICommandSender iCommandSender, String[] args)
    {
        if (iCommandSender instanceof EntityPlayerMP)
        {
            //TODO
        }
    }
    
    public GradeType getPermissionType()
    {
        return GradeType.DONATOR;
    }
}