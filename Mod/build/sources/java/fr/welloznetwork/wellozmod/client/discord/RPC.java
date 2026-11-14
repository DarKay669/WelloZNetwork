package fr.welloznetwork.wellozmod.client.discord;

import net.minecraft.client.Minecraft;

public class RPC
{    
    public static void init()
    {
        IDiscordRPC discordRPC = IDiscordRPC.INSTANCE;
        discordRPC.Discord_Initialize("1109755580014395433", new DiscordEventHandlers(), true, "");
        
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
                discordRichPresence.smallImageText = Minecraft.getMinecraft().getSession().getUsername();
                
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