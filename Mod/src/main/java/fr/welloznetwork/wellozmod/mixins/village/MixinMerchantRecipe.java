package fr.welloznetwork.wellozmod.mixins.village;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;

@Mixin(MerchantRecipe.class)
public class MixinMerchantRecipe
{
    @Shadow
    private ItemStack itemToBuy;
    
    @Shadow
    private ItemStack secondItemToBuy;
    
    @Shadow
    private ItemStack itemToSell;
    
    @Overwrite
    public boolean hasSameIDsAs(MerchantRecipe merchantRecipe)
    {
        return (this.itemToBuy.getItem() == merchantRecipe.getItemToBuy().getItem() && this.itemToBuy.getItemDamage() == merchantRecipe.getItemToBuy().getItemDamage()) && (this.itemToSell.getItem() == merchantRecipe.getItemToSell().getItem() && this.itemToSell.getItemDamage() == merchantRecipe.getItemToSell().getItemDamage()) ? this.secondItemToBuy == null && merchantRecipe.getSecondItemToBuy() == null || this.secondItemToBuy != null && merchantRecipe.getSecondItemToBuy() != null && (this.secondItemToBuy.getItem() == merchantRecipe.getSecondItemToBuy().getItem() && this.secondItemToBuy.getItemDamage() == merchantRecipe.getSecondItemToBuy().getItemDamage()) : false;
    }
}