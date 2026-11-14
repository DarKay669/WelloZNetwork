package fr.welloznetwork.wellozmod.inventory;

import fr.welloznetwork.wellozmod.item.crafting.SmithingTableRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerSmithingTable extends Container
{
    private final World world;
    private final InventoryCrafting inputMatrix = new InventoryCrafting(this, 2, 1);
    private final InventoryCraftResult resultInventory = new InventoryCraftResult();
    private final Slot applicant = new Slot(inputMatrix, 0, 27, 47), ingot = new Slot(inputMatrix, 1, 76, 47), result;
    
    public ContainerSmithingTable(InventoryPlayer inventoryPlayer, World world)
    {
        this.world = world;
        addSlotToContainer(this.applicant);
        addSlotToContainer(this.ingot);
        this.result = addSlotToContainer(new SlotSmithingResult(inventoryPlayer.player, this.inputMatrix, this.resultInventory, 2, 134, 47));
        
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        
        for (int y = 0; y < 9; y++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, y, 8 + y * 18, 142));
        }
    }
    
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }
    
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);
        
        if (!this.world.isRemote)
        {
            for (int i = 0; i < this.inputMatrix.getSizeInventory(); i++)
            {
                ItemStack itemStack = this.inputMatrix.getStackInSlotOnClosing(i);
                
                if (itemStack != null)
                {
                    entityPlayer.dropPlayerItemWithRandomChoice(itemStack, false);
                }
            }
        }
    }
    
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int index)
    {
        final Object s = this.inventorySlots.get(index);
        
        if (!(s instanceof Slot))
        {
            return null;
        }
        
        final Slot slot = (Slot) s;
        
        if (!slot.getHasStack())
        {
            return null;
        }
        
        final ItemStack newStack = slot.getStack();
        final ItemStack oldStack = newStack.copy();
        boolean merged = false;
        
        if (index <= 2)
        {
            merged = mergeItemStack(newStack, 3, 39, true);
        }
        
        else if (index < 30)
        {
            merged = this.tryUpper(newStack) || mergeItemStack(newStack, 30, 39, false);
        }
        
        else
        {
            merged = this.tryUpper(newStack) || mergeItemStack(newStack, 3, 30, false);
        }
        
        if (!merged)
        {
            return null;
        }
        
        if (newStack.stackSize == 0)
        {
            slot.putStack(null);
        }
        
        else
        {
            slot.onSlotChanged();
        }
        
        slot.onPickupFromSlot(entityPlayer, newStack);
        return oldStack;
    }
    
    public void onCraftMatrixChanged(IInventory iinventory)
    {
        this.resultInventory.setInventorySlotContents(0, SmithingTableRecipes.getInstance().findMatchingRecipe(this.inputMatrix, this.world));
        super.onCraftMatrixChanged(iinventory);
    }
    
    private boolean tryUpper(ItemStack itemStack)
    {
        return !this.applicant.getHasStack() && mergeItemStack(itemStack, 1, 1, false) || !this.ingot.getHasStack() && mergeItemStack(itemStack, 0, 2, false);
    }
    
    public boolean unable()
    {
        return (this.applicant.getHasStack() || this.ingot.getHasStack()) && !this.result.getHasStack();
    }
}