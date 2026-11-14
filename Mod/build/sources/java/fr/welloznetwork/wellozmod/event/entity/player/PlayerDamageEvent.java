package fr.welloznetwork.wellozmod.event.entity.player;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;

@Cancelable
public class PlayerDamageEvent extends Event
{
    public EntityPlayer entityPlayer;
    private DamageSource damageSource;
    public float amount;
    
    public PlayerDamageEvent(EntityPlayer entityPlayer, DamageSource damageSource, float amount)
    {
        this.entityPlayer = entityPlayer;
        this.damageSource = damageSource;
        this.amount = amount;
    }
    
    private static float post(EntityPlayer entityPlayer, DamageSource damageSource, float amount)
    {
        PlayerDamageEvent event = new PlayerDamageEvent(entityPlayer, damageSource, amount);
        MinecraftForge.EVENT_BUS.post(event);
        
        return event.isCanceled() ? 0.0F : event.amount;
    }
}