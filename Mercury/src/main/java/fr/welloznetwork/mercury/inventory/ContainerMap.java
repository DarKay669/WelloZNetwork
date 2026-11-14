package fr.welloznetwork.mercury.inventory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import fr.welloznetwork.mercury.init.RegistryItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponentTranslation;

public class ContainerMap extends Container
{
    private static final int BEDWARS = 2, FACTION = 4, MINAGE = 6;
    
    private final IInventory inventory;
    
    public ContainerMap(InventoryPlayer inventoryPlayer)
    {
        this.inventory = new InventoryBasic("", false, 9);
        this.inventory.setInventorySlotContents(BEDWARS, new ItemStack(Items.bed, 1));
        this.inventory.setInventorySlotContents(FACTION, new ItemStack(RegistryItems.faction, 1));
        this.inventory.setInventorySlotContents(MINAGE, new ItemStack(RegistryItems.minage, 1));
        
        for (int i = 0; i < 9; i++)
        {
            this.addSlotToContainer(new SlotLocked(this.inventory, i, 8 + i * 18, 6));
        }
    }
    
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer entityPlayer)
    {
        if (slotId >= 0 && slotId < this.inventory.getSizeInventory())
        {
            if (this.inventory.getStackInSlot(slotId) != null)
            {
                switch (slotId)
                {
                    case BEDWARS:
                        break;
                    
                    case FACTION:
                        entityPlayer.addChatMessage(new ChatComponentTranslation("server.faction.teleport"));
                        this.teleportToServer(entityPlayer, "faction");
                        break;
                        
                    case MINAGE:
                        entityPlayer.addChatMessage(new ChatComponentTranslation("server.minage.teleport"));
                        this.teleportToServer(entityPlayer, "minage");
                        break;
                }
            }
        }
        
        return super.slotClick(slotId, clickedButton, mode, entityPlayer);
    }
    
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return false;
    }
    
    private void teleportToServer(EntityPlayer entityPlayer, String serverName)
    {
        if (entityPlayer instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityPlayer;
            
            try
            {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                
                dataOutputStream.writeUTF("Connect");
                dataOutputStream.writeUTF(serverName);
                
                entityPlayerMP.playerNetServerHandler.sendPacket(new C17PacketCustomPayload("BungeeCord", byteArrayOutputStream.toByteArray()));
            }
            
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}