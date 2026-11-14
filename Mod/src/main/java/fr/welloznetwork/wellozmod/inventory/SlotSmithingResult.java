package fr.welloznetwork.wellozmod.inventory;

import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotSmithingResult extends SlotCrafting
{
    public SlotSmithingResult(EntityPlayer entityPlayer, IInventory iinventory, IInventory result, int index, int x, int y)
    {
        super(entityPlayer, iinventory, result, index, x, y);
    }
    
    public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack)
    {
        entityPlayer.worldObj.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.SOUND_TYPE_SMITHING_TABLE_USE[entityPlayer.worldObj.rand.nextInt(RegistrySounds.SOUND_TYPE_SMITHING_TABLE_USE.length)], 1.0F, 1.0F);
        
        super.onPickupFromSlot(entityPlayer, itemStack);
    }
}