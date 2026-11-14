package fr.welloznetwork.venus.lib.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public interface IProxy
{
    void preInit(FMLPreInitializationEvent event);
    
    void init(FMLInitializationEvent event);
    
    void postInit(FMLPostInitializationEvent event);
    
    void loadComplete(FMLLoadCompleteEvent event);
    
    void serverStarting(FMLServerStartingEvent event);
}