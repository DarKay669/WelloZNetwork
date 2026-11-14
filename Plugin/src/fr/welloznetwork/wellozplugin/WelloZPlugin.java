package fr.welloznetwork.wellozplugin;

import org.bukkit.plugin.java.JavaPlugin;

import fr.welloznetwork.wellozplugin.sanction.command.CommandSS;
import fr.welloznetwork.wellozplugin.sanction.command.CommandSSBan;
import fr.welloznetwork.wellozplugin.sanction.command.CommandSSMute;
import fr.welloznetwork.wellozplugin.sanction.listener.EventListener;

public class WelloZPlugin extends JavaPlugin
{
    public void onEnable()
    {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getCommand("ss").setExecutor(new CommandSS());
        this.getCommand("ss-ban").setExecutor(new CommandSSBan());
        this.getCommand("ss-mute").setExecutor(new CommandSSMute());
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}