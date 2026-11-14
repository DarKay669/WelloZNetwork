package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCoffeeMix extends ItemFood
{
    public ItemCoffeeMix()
    {
        super(8, 0.4F, false);
        setMaxStackSize(1);
        setUnlocalizedName("coffee_mix");
        setTextureName("wellozmod:coffee_mix");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.drink;
    }
    
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        return new ItemStack(RegistryItems.empty_coffee);
    }
}