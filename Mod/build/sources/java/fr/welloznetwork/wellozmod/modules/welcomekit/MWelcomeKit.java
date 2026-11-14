package fr.welloznetwork.wellozmod.modules.welcomekit;

import fr.welloznetwork.wellozmod.modules.IModule;
import fr.welloznetwork.wellozmod.modules.Module;
import fr.welloznetwork.wellozmod.modules.ModuleHelper;
import fr.welloznetwork.wellozmod.modules.welcomekit.eep.WelcomeKitEEP;
import fr.welloznetwork.wellozmod.modules.welcomekit.listener.WelcomeKitListener;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedProperty;
import fr.welloznetwork.wellozmod.util.ModUtil.ExtendedUtils;
import net.minecraft.entity.player.EntityPlayer;

@Module(id = "welcomekit", description = "Give a start kit to new player")
public class MWelcomeKit implements IModule
{
    public void init() 
    {
        ExtendedUtils.init(EntityPlayer.class, WelcomeKitEEP.class, "welcome_eep", new ExtendedProperty[] {ExtendedProperty.SELF_CONSTRUCT, ExtendedProperty.PERSISTANT, ExtendedProperty.SYNCHRONIZED});
        
        ModuleHelper.addListener(new Class[] {WelcomeKitListener.class});
    }
}