package fr.welloznetwork.wellozmod.modules;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

public class ModuleHelper
{
    public static void addListener(Class<?>... listeners)
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