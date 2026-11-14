package fr.welloznetwork.wellozmod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerEmpty extends Container
{
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }
    
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int index)
    {
        return null;
    }
}