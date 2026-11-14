package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCookedRabbit extends ItemFood
{
	public ItemCookedRabbit()
	{
		super(5, true);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("cooked_rabbit");
		setTextureName("wellozmod:cooked_rabbit");
	}
}