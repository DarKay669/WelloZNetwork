package fr.welloznetwork.venus.lib.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.common.MinecraftForge;

public abstract class AProxy implements IProxy
{
    private SimpleNetworkWrapper simpleNetworkWrapper;
    
    public void preInit(FMLPreInitializationEvent event) {}
    
    public void init(FMLInitializationEvent event) {}
    
    public void postInit(FMLPostInitializationEvent event) {}
    
    public void loadComplete(FMLLoadCompleteEvent event) {}
    
    public void serverStarting(FMLServerStartingEvent event) {}
    
    public void initNetwork(String channelName)
    {
        this.simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(channelName.toUpperCase());
    }
    
    public SimpleNetworkWrapper getSimpleNetworkWrapper()
    {
        return this.simpleNetworkWrapper;
    }
    
    protected void addListener(Class<?>... listeners)
    {
        for (Class<?> listener : listeners)
        {
            try
            {
                MinecraftForge.EVENT_BUS.register(listener.newInstance());
                FMLCommonHandler.instance().bus().register(listener.newInstance());
            }
            
            catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }
}