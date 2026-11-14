package fr.welloznetwork.wellozmod.client.discord;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordEventHandlers extends com.sun.jna.Structure
{
    private static final List<String> FIELD_ORDER = Collections.unmodifiableList(Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest"));
    
    public OnReady ready;
    public OnStatus disconnected, errored;
    public OnGameUpdate joinGame, spectateGame;
    public OnJoinRequest joinRequest;
    
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        
        if (!(o instanceof DiscordEventHandlers))
        {
            return false;
        }
        
        DiscordEventHandlers discordEventHandlers = (DiscordEventHandlers) o;
        
        return (Objects.equals(this.ready, this.ready) && Objects.equals(this.disconnected, this.disconnected) && Objects.equals(this.errored, this.errored) && Objects.equals(this.joinGame, this.joinGame) && Objects.equals(this.spectateGame, this.spectateGame) && Objects.equals(this.joinRequest, this.joinRequest));
    }
    
    public int hashCode()
    {
        return Objects.hash(new Object[] {this.ready, this.disconnected, this.errored, this.joinGame, this.spectateGame, this.joinRequest});
    }
    
    protected List<String> getFieldOrder()
    {
        return FIELD_ORDER;
    }
    
    public static interface OnJoinRequest extends com.sun.jna.Callback
    {
        void accept(DiscordUser paramDiscordUser);
    }
    
    public static interface OnGameUpdate extends com.sun.jna.Callback
    {
        void accept(String paramString);
    }
    
    public static interface OnStatus extends com.sun.jna.Callback
    {
        void accept(int paramInt, String paramString);
    }
    
    public static interface OnReady extends com.sun.jna.Callback
    {
        void accept(DiscordUser paramDiscordUser);
    }
}