package fr.welloznetwork.wellozmod.modules.hud.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class HUDListener
{
    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event)
    {
        if (event.entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) event.entityLiving;
            
            if (entityPlayer.moveForward > 0.0F && !entityPlayer.isSneaking())
            {
                if (RegistrySettings.toggleSprintEnabled)
                {
                    entityPlayer.setSprinting(true);
                }
            }
        }
    }
}