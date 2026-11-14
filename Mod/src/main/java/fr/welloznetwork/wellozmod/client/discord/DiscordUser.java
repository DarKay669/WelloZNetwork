package fr.welloznetwork.wellozmod.client.discord;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DiscordUser extends com.sun.jna.Structure
{
    private static final List<String> FIELD_ORDER = Collections.unmodifiableList(Arrays.asList(new String[] {"userId", "username", "discriminator", "avatar"}));
    
    public String userId, username, discriminator, avatar;
    
    public DiscordUser(String encoding)
    {
        super();
    }
    
    public DiscordUser()
    {
        this("UTF-8");
    }
    
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        
        if (!(o instanceof DiscordUser))
        {
            return false;
        }
        
        DiscordUser discordUser = (DiscordUser) o;
        
        return (Objects.equals(this.userId, this.userId) && Objects.equals(this.username, this.username) && Objects.equals(this.discriminator, this.discriminator) && Objects.equals(this.avatar, this.avatar));
    }
    
    public int hashCode()
    {
        return Objects.hash(new Object[] {this.userId, this.username, this.discriminator, this.avatar});
    }
    
    protected List<String> getFieldOrder()
    {
        return FIELD_ORDER;
    }
}