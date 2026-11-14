package fr.welloznetwork.wellozmod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEmeraldChest extends Container
{
    private IInventory iinventory;
    
    public ContainerEmeraldChest(InventoryPlayer inventoryPlayer, IInventory iinventory)
    {
        this.iinventory = iinventory;
        inventoryPlayer.openInventory();
        int row = 0;
        
        for (row = 0; row < 9; row++)
        {
            for (int column = 0; column < 12; column++)
            {
                this.addSlotToContainer(new Slot(iinventory, column + row * 12, 12 + column * 18, 8 + row * 18));
            }
        }
        
        for (row = 0; row < 3; row++)
        {
            for (int column = 0; column < 9; ++column)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, column + row * 9 + 9, 39 + column * 18, 174 + row * 18));
            }
        }
        
        for (int column = 0; column < 9; column++)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, column, 39 + column * 18, 232));
        }
    }
    
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int index)
    {
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            
            if (index < 9 * 12)
            {
                if (!this.mergeItemStack(itemStack1, 9 * 12, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            
            else
            {
                if (!this.mergeItemStack(itemStack1, 0, 9 * 12, false))
                {
                    return null;
                }
            }
            
            if (itemStack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            
            else
            {
                slot.onSlotChanged();
            }
        }
        
        return itemStack;
    }
    
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);
        this.iinventory.closeInventory();
    }
    
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return this.iinventory.isUseableByPlayer(entityPlayer);
    }
}