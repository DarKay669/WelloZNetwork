package fr.welloznetwork.venus.internal.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.internal.common.CommonProxy;
import fr.welloznetwork.venus.lib.dev.Debug;
import fr.welloznetwork.venus.lib.loader.Loader;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy 
{
    public void loadComplete(FMLLoadCompleteEvent event)
    {
        if (!Debug.isDebugMode())
        {
            Loader.loadComplete(reason ->
            {
                if (Minecraft.getMinecraft() != null)
                {
                    Minecraft.getMinecraft().shutdown();
                }
                
                FMLCommonHandler.instance().exitJava(-1, true);
            });
        }
        
        super.loadComplete(event);
    }
}