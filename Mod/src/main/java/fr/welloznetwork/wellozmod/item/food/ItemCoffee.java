package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCoffee extends ItemFood
{
    public ItemCoffee()
    {
        super(12, 0.8F, false);
        setMaxStackSize(1);
        setUnlocalizedName("coffee");
        setTextureName("wellozmod:coffee");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.drink;
    }
    
    public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 300, 0));
    }
    
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        return new ItemStack(RegistryItems.empty_coffee);
    }
}