package fr.welloznetwork.mercury.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import fr.welloznetwork.mercury.Mercury;
import fr.welloznetwork.mercury.common.register.ServerEventRegister;
import fr.welloznetwork.mercury.init.RegistryGui;
import fr.welloznetwork.mercury.init.RegistryItems;
import fr.welloznetwork.venus.lib.proxy.AProxy;

public class CommonProxy extends AProxy
{
    public void init(FMLInitializationEvent event) 
    {
        RegistryItems.init();
        
        this.addListener(new Class[] {ServerEventRegister.class});
        
        NetworkRegistry.INSTANCE.registerGuiHandler(Mercury.instance, new RegistryGui());
    }
}