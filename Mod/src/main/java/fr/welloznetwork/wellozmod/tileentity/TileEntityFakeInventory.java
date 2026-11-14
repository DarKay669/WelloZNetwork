package fr.welloznetwork.wellozmod.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public final class TileEntityFakeInventory extends TileEntity implements IInventory, ISidedInventory
{
    private final ISidedInventory iSidedInventory;
    
    public TileEntityFakeInventory(ISidedInventory iSidedInventory)
    {
        this.iSidedInventory = iSidedInventory;
    }
    
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return this.iSidedInventory.getAccessibleSlotsFromSide(side);
    }
    
    public boolean canInsertItem(int index, ItemStack itemStack, int direction)
    {
        return this.iSidedInventory.canInsertItem(index, itemStack, direction);
    }
    
    public boolean canExtractItem(int index, ItemStack itemStack, int direction)
    {
        return this.iSidedInventory.canExtractItem(index, itemStack, direction);
    }
    
    public int getSizeInventory()
    {
        return this.iSidedInventory.getSizeInventory();
    }
    
    public ItemStack getStackInSlot(int index)
    {
        return this.iSidedInventory.getStackInSlot(index);
    }
    
    public ItemStack decrStackSize(int index_, int count)
    {
        return this.iSidedInventory.decrStackSize(index_, count);
    }
    
    public ItemStack getStackInSlotOnClosing(int index)
    {
        return this.iSidedInventory.getStackInSlotOnClosing(index);
    }
    
    public void setInventorySlotContents(int index, ItemStack itemStack)
    {
        this.iSidedInventory.setInventorySlotContents(index, itemStack);
    }
    
    public String getInventoryName()
    {
        return this.iSidedInventory.getInventoryName();
    }
    
    public boolean hasCustomInventoryName()
    {
        return this.iSidedInventory.hasCustomInventoryName();
    }
    
    public int getInventoryStackLimit()
    {
        return this.iSidedInventory.getInventoryStackLimit();
    }
    
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return this.iSidedInventory.isUseableByPlayer(entityPlayer);
    }
    
    public void openInventory()
    {
        this.iSidedInventory.openInventory();
    }
    
    public void closeInventory()
    {
        this.iSidedInventory.closeInventory();
    }
    
    public boolean isItemValidForSlot(int index, ItemStack itemStack)
    {
        return this.iSidedInventory.isItemValidForSlot(index, itemStack);
    }
}