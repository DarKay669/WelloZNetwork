package fr.welloznetwork.wellozmod.modules.hud;

import cpw.mods.fml.relauncher.Side;
import fr.welloznetwork.wellozmod.modules.IModule;
import fr.welloznetwork.wellozmod.modules.Module;
import fr.welloznetwork.wellozmod.modules.ModuleHelper;
import fr.welloznetwork.wellozmod.modules.hud.client.listener.HUDClientListener;
import fr.welloznetwork.wellozmod.modules.hud.listener.HUDListener;

@Module(id = "hud", description = "HUD mods like : blur, cps, togglesprint...", side = Side.CLIENT)
public class MHUD implements IModule
{
    public static final HUDClientListener CLIENT_LISTENER = new HUDClientListener();
    
    public void init()
    {
        ModuleHelper.addListener(new Class[] {HUDListener.class});
        ModuleHelper.addListener(new Class[] {HUDClientListener.class});
        
        CLIENT_LISTENER.init();
    }
}