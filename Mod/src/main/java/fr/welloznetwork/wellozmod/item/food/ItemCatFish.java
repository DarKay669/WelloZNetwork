package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCatFish extends ItemFood
{
	public ItemCatFish() 
	{
		super(6, true);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("cat_fish");
		setTextureName("wellozmod:cat_fish");
	}	
}