package fr.welloznetwork.wellozlauncher.client.discord;

import fr.welloznetwork.wellozlauncher.Constants;
import fr.welloznetwork.wellozlauncher.WelloZLauncher;

public class RPC
{    
    public static void init()
    {
        IDiscordRPC discordRPC = IDiscordRPC.INSTANCE;
        discordRPC.Discord_Initialize(Constants.DISCORD_APP_ID, new DiscordEventHandlers(), true, "");
        
        DiscordRichPresence discordRichPresence = new DiscordRichPresence();
        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        
        discordRPC.Discord_UpdatePresence(discordRichPresence);
        
        new Thread(() -> 
        {
            while (!Thread.currentThread().isInterrupted())
            {
                discordRPC.Discord_RunCallbacks();
                
                discordRichPresence.largeImageKey = "largeimagekey";
                discordRichPresence.largeImageText = "www.welloznetwork.fr";
                discordRichPresence.smallImageKey = "smallimagekey";
                
                if (WelloZLauncher.getInstance().isLoggedIn())
                {
                    discordRichPresence.smallImageText = WelloZLauncher.getInstance().getAuthInfos().getUsername();
                }
                
                discordRPC.Discord_UpdatePresence(discordRichPresence);
                
                try
                {
                    Thread.sleep(2000L);
                }
                
                catch (InterruptedException e) {}
            }
        }, "Discord-RPC").start();
    }
}