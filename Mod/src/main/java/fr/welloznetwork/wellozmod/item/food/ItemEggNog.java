package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEggNog extends ItemFood
{
    public ItemEggNog()
    {
        super(12, 0.7F, false);
        setMaxStackSize(1);
        setUnlocalizedName("egg_nog");
        setTextureName("wellozmod:egg_nog");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.drink;
    }
    
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        super.onEaten(itemStack, world, entityPlayer);
        return new ItemStack(RegistryItems.empty_coffee);
    }
}