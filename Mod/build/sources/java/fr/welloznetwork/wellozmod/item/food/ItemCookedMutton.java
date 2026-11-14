package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCookedMutton extends ItemFood
{
	public ItemCookedMutton()
	{
		super(6, true);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("cooked_mutton");
		setTextureName("wellozmod:cooked_mutton");
	}
}