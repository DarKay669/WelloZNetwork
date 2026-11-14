package fr.welloznetwork.venus.lib.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CooldownUtils
{
    private static final Map<String, Map<UUID, Long>> COOLDOWN_MAP = new HashMap<>();
    
    public static void init(Entity entity, String key)
    {
        init(entity.getUniqueID(), key);
    }
    
    public static void init(UUID uuid, String key)
    {
        if (!COOLDOWN_MAP.containsKey(key))
        {
            COOLDOWN_MAP.put(key, new HashMap<>());
        }
        
        ((Map<UUID, Long>) COOLDOWN_MAP.get(key)).put(uuid, Long.valueOf(0L));
    }
    
    public static void setCooldown(Entity entity, String key, long time)
    {
        setCooldown(entity.getUniqueID(), key, time);
    }
    
    public static void setCooldown(UUID uuid, String key, long time)
    {
        if (!COOLDOWN_MAP.containsKey(key))
        {
            init(uuid, key);
        }
        
        ((Map<UUID, Long>) COOLDOWN_MAP.get(key)).put(uuid, Long.valueOf(System.currentTimeMillis() + time));
    }
    
    public static void removeCooldown(Entity entity, String key)
    {
        removeCooldown(entity.getUniqueID(), key);
    }
    
    public static void removeCooldown(UUID uuid, String key)
    {
        if (!COOLDOWN_MAP.containsKey(key))
        {
            return;
        }
        
        ((Map) COOLDOWN_MAP.get(key)).remove(uuid);
    }
    
    public static boolean hasCooldown(EntityPlayer entityPlayer, String key)
    {
        return hasCooldown(entityPlayer.getUniqueID(), key);
    }
    
    public static boolean hasCooldown(UUID uuid, String key)
    {
        if (!COOLDOWN_MAP.containsKey(key))
        {
            return false;
        }
        
        return ((Map) COOLDOWN_MAP.get(key)).containsKey(key);
    }
    
    public static boolean isOnCooldown(Entity entity, String key)
    {
        return isOnCooldown(entity.getUniqueID(), key);
    }
    
    public static boolean isOnCooldown(UUID uuid, String key)
    {
        return (getCooldown(uuid, key) > System.currentTimeMillis());
    }
    
    public static long getCooldown(Entity entity, String key)
    {
        return getCooldown(entity.getUniqueID(), key);
    }
    
    public static long getCooldown(UUID uuid, String key)
    {
        if (!hasCooldown(uuid, key))
        {
            return 0L;
        }
        
        return ((Long) ((Map) COOLDOWN_MAP.get(key)).getOrDefault(uuid, Long.valueOf(0L))).longValue();
    }
}