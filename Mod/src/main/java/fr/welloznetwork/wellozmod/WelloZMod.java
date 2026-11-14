package fr.welloznetwork.wellozmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import fr.welloznetwork.wellozmod.common.CommonProxy;
import fr.welloznetwork.wellozmod.modules.ModuleManager;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.MOD_VERSION, dependencies = Constants.MOD_DEPENDENCIES, acceptedMinecraftVersions = Constants.MOD_ACCEPTED_MINECRAFT_VERSIONS)
public class WelloZMod
{
	@Instance(Constants.MOD_ID)
	public static WelloZMod instance;
	
	@SidedProxy(clientSide = Constants.MOD_CLIENTSIDE, serverSide = Constants.MOD_SERVERSIDE)
	public static CommonProxy proxy;
	
	private static ModuleManager moduleManager = new ModuleManager();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	    proxy.preInit(event);
	    moduleManager.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
	    proxy.init(event);
	    moduleManager.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	    proxy.postInit(event);
	}
	
	@EventHandler
    public void loadComplete(FMLLoadCompleteEvent event)
    {
	    proxy.loadComplete(event);
    }
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
	    proxy.serverStarting(event);
	}
	
	public static ModuleManager getModuleManager()
	{
	    return moduleManager;
	}
}