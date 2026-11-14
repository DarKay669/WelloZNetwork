package fr.welloznetwork.wellozmod.mixins.inventory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;

@Mixin(SlotMerchantResult.class)
public class MixinSlotMerchantResult
{
    @Overwrite
    public boolean func_75230_a(MerchantRecipe merchantRecipe, ItemStack itemStack, ItemStack itemStack1)
    {
        ItemStack itemToBuy = merchantRecipe.getItemToBuy();
        ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
        
        if (itemStack != null && itemStack.getItem() == itemToBuy.getItem() && (itemStack.getItemDamage() == itemToBuy.getItemDamage()))
        {
            if (secondItemToBuy != null && itemStack1 != null && secondItemToBuy.getItem() == itemStack1.getItem() && secondItemToBuy.getItemDamage() == itemStack1.getItemDamage())
            {
                itemStack.stackSize -= itemToBuy.stackSize;
                secondItemToBuy.stackSize -= secondItemToBuy.stackSize;
                
                return true;
            }
            
            if (secondItemToBuy == null && itemStack1 == null)
            {
                itemStack.stackSize -= itemToBuy.stackSize;
                
                return true;
            }
        }
        
        return false;
    }
}