package fr.welloznetwork.wellozmod.modules.welcomekit.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import fr.welloznetwork.wellozmod.modules.welcomekit.eep.WelcomeKitEEP;
import net.minecraft.entity.player.EntityPlayerMP;

public class WelcomeKitListener
{
    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerLoggedInEvent event)
    {
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.player;
        
        if (entityPlayerMP == null)
        {
            return;
        }
        
        WelcomeKitEEP welcomeEEP = WelcomeKitEEP.get(entityPlayerMP);
        
        if (welcomeEEP != null && !welcomeEEP.isWelcomeKit())
        {
            welcomeEEP.giveWelcomeKit(entityPlayerMP);
        }
    }
}