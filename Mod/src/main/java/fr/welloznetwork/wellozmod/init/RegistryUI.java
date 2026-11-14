package fr.welloznetwork.wellozmod.init;

import cpw.mods.fml.common.network.IGuiHandler;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.client.ui.UIShop;
import fr.welloznetwork.wellozmod.client.ui.inventory.UIEmeraldChest;
import fr.welloznetwork.wellozmod.client.ui.inventory.UIOnlineDetector;
import fr.welloznetwork.wellozmod.client.ui.inventory.UISmithingTable;
import fr.welloznetwork.wellozmod.inventory.ContainerEmeraldChest;
import fr.welloznetwork.wellozmod.inventory.ContainerEmpty;
import fr.welloznetwork.wellozmod.inventory.ContainerSmithingTable;
import fr.welloznetwork.wellozmod.network.play.server.S03PacketMoney;
import fr.welloznetwork.wellozmod.tileentity.TileEntityBarrel;
import fr.welloznetwork.wellozmod.tileentity.TileEntityOnlineDetector;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;

public class RegistryUI implements IGuiHandler
{
    public static final int EMERALD_CHEST = 0, BARREL = 1, EDIT_SIGN = 2, ONLINE_DETECTOR = 3, SMITHING_TABLE = 4, TRAVELING_VILLAGER = 5;
    
    public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case EMERALD_CHEST:
                return new ContainerEmeraldChest(entityPlayer.inventory, (IInventory) world.getTileEntity(x, y, z));
                
            case BARREL:
                return new ContainerChest(entityPlayer.inventory, (TileEntityBarrel) world.getTileEntity(x, y, z));
                
            case EDIT_SIGN:
                return new ContainerEmpty();
                
            case ONLINE_DETECTOR:
                return new ContainerEmpty();
                
            case SMITHING_TABLE:
                return new ContainerSmithingTable(entityPlayer.inventory, world);
                
            case TRAVELING_VILLAGER:
                return new ContainerEmpty();
        }
        
        return null;
    }
    
    public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        switch (ID) 
        {
            case EMERALD_CHEST:
                return new UIEmeraldChest(entityPlayer.inventory, (IInventory) world.getTileEntity(x, y, z));
                
            case BARREL:
                return new GuiChest(entityPlayer.inventory, (TileEntityBarrel) world.getTileEntity(x, y, z));
                
            case EDIT_SIGN:
                return new GuiEditSign((TileEntitySign) world.getTileEntity(x, y, z));
                
            case ONLINE_DETECTOR:
                return new UIOnlineDetector((TileEntityOnlineDetector) world.getTileEntity(x, y, z));
                
            case SMITHING_TABLE:
                return new UISmithingTable(new ContainerSmithingTable(entityPlayer.inventory, world));
                
            case TRAVELING_VILLAGER:
                WelloZMod.proxy.getNetworkManager().sendToServer(new S03PacketMoney());
                return new UIShop();
        }
        
        return null;
    }
}