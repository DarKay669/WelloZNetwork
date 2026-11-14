package fr.welloznetwork.mercury.init;

import cpw.mods.fml.common.network.IGuiHandler;
import fr.welloznetwork.mercury.client.ui.UIMap;
import fr.welloznetwork.mercury.inventory.ContainerMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class RegistryGui implements IGuiHandler
{
    public static final int MAP = 0;
    
    public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case MAP:
                return new ContainerMap(entityPlayer.inventory);
        }
        
        return null;
    }
    
    public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case MAP:
                return new UIMap(new ContainerMap(entityPlayer.inventory));
        }
        
        return null;
    }
}