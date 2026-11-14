package fr.welloznetwork.wellozmod.init;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.command.CommandClearLag;
import fr.welloznetwork.wellozmod.command.CommandColors;
import fr.welloznetwork.wellozmod.command.CommandCraft;
import fr.welloznetwork.wellozmod.command.CommandDeathLocate;
import fr.welloznetwork.wellozmod.command.CommandDelhome;
import fr.welloznetwork.wellozmod.command.CommandDiscord;
import fr.welloznetwork.wellozmod.command.CommandEnderChest;
import fr.welloznetwork.wellozmod.command.CommandFeed;
import fr.welloznetwork.wellozmod.command.CommandFurnace;
import fr.welloznetwork.wellozmod.command.CommandGrade;
import fr.welloznetwork.wellozmod.command.CommandHeal;
import fr.welloznetwork.wellozmod.command.CommandHelp;
import fr.welloznetwork.wellozmod.command.CommandHome;
import fr.welloznetwork.wellozmod.command.CommandInvsee;
import fr.welloznetwork.wellozmod.command.CommandListPlayers;
import fr.welloznetwork.wellozmod.command.CommandLobby;
import fr.welloznetwork.wellozmod.command.CommandMoney;
import fr.welloznetwork.wellozmod.command.CommandNickname;
import fr.welloznetwork.wellozmod.command.CommandNightVision;
import fr.welloznetwork.wellozmod.command.CommandNotification;
import fr.welloznetwork.wellozmod.command.CommandPay;
import fr.welloznetwork.wellozmod.command.CommandPlayerHead;
import fr.welloznetwork.wellozmod.command.CommandRandomTeleport;
import fr.welloznetwork.wellozmod.command.CommandRepair;
import fr.welloznetwork.wellozmod.command.CommandSetSpawn;
import fr.welloznetwork.wellozmod.command.CommandSethome;
import fr.welloznetwork.wellozmod.command.CommandSpawn;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.command.CommandTPA;
import fr.welloznetwork.wellozmod.command.CommandTPAHere;
import fr.welloznetwork.wellozmod.command.CommandTPAccept;
import fr.welloznetwork.wellozmod.command.CommandTPDeny;

public class RegistryCommands
{
    public static void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandDelhome());
        event.registerServerCommand(new CommandDiscord());
        event.registerServerCommand(new CommandFeed());
        event.registerServerCommand(new CommandFurnace());
        event.registerServerCommand(new CommandGrade());
        event.registerServerCommand(new CommandHeal());
        event.registerServerCommand(new CommandHelp());
        event.registerServerCommand(new CommandHome());
        event.registerServerCommand(new CommandInvsee());
        event.registerServerCommand(new CommandClearLag());
        event.registerServerCommand(new CommandListPlayers());
        event.registerServerCommand(new CommandLobby());
        event.registerServerCommand(new CommandMoney());
        event.registerServerCommand(new CommandNickname());
        event.registerServerCommand(new CommandNotification());
        event.registerServerCommand(new CommandPay());
        event.registerServerCommand(new CommandPlayerHead());
        event.registerServerCommand(new CommandRandomTeleport());
        event.registerServerCommand(new CommandRepair());
        event.registerServerCommand(new CommandSethome());
        event.registerServerCommand(new CommandSetSpawn());
        event.registerServerCommand(new CommandSpawn());
        event.registerServerCommand(new CommandSpectator());
        event.registerServerCommand(new CommandTPA());
        event.registerServerCommand(new CommandTPAccept());
        event.registerServerCommand(new CommandTPAHere());
        event.registerServerCommand(new CommandTPDeny());
        
        if (Constants.V2)
        {
            event.registerServerCommand(new CommandColors());
            event.registerServerCommand(new CommandCraft());
            event.registerServerCommand(new CommandDeathLocate());
            event.registerServerCommand(new CommandEnderChest());
            event.registerServerCommand(new CommandNightVision());
        }
    }
}