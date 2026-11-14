package fr.welloznetwork.mercury.common.register;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import fr.welloznetwork.mercury.init.RegistryItems;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ServerEventRegister
{
    public ServerEventRegister()
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }
    
    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerLoggedInEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.player;
            
            entityPlayerMP.inventory.setInventorySlotContents(4, new ItemStack(RegistryItems.map));
            entityPlayerMP.inventory.currentItem = 4;
        }
    }
}