package fr.welloznetwork.mercury.client.register;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class ClientEventRegister
{
    public ClientEventRegister()
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }
    
    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event)
    {
        if ((Minecraft.getMinecraft()).thePlayer.capabilities.isCreativeMode)
        {
            return;
        }
        
        if (event.type != ElementType.HEALTH && event.type != ElementType.EXPERIENCE && event.type != ElementType.FOOD)
        {
            return;
        }
        
        event.setCanceled(true);
    }
}