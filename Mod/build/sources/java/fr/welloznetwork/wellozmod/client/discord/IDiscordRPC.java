package fr.welloznetwork.wellozmod.client.discord;

public interface IDiscordRPC extends com.sun.jna.Library
{
    public static final IDiscordRPC INSTANCE = (IDiscordRPC) com.sun.jna.Native.loadLibrary("discord-rpc", IDiscordRPC.class);
    
    public static final int DISCORD_REPLY_NO = 0;
    
    public static final int DISCORD_REPLY_YES = 1;
    
    public static final int DISCORD_REPLY_IGNORE = 2;
    
    void Discord_Initialize(String paramString1, DiscordEventHandlers paramDiscordEventHandlers, boolean paramBoolean, String paramString2);
    
    void Discord_Shutdown();
    
    void Discord_RunCallbacks();
    
    void Discord_UpdateConnection();
    
    void Discord_UpdatePresence(DiscordRichPresence paramDiscordRichPresence);
    
    void Discord_ClearPresence();
    
    void Discord_Respond(String paramString, int paramInt);
    
    void Discord_UpdateHandlers(DiscordEventHandlers paramDiscordEventHandlers);
    
    void Discord_Register(String paramString1, String paramString2);
    
    void Discord_RegisterSteamGame(String paramString1, String paramString2);
}