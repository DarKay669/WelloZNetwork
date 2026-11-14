package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTotemOfUndying extends Item
{    
	public ItemTotemOfUndying()
	{
		setUnlocalizedName("totem_of_undying");
		setTextureName("wellozmod:totem_of_undying");
		setMaxStackSize(1);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.uncommon;
    }
}