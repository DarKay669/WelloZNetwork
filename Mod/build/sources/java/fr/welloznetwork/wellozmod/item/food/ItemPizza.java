package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemPizza extends ItemFood
{
	public ItemPizza()
	{
		super(10, true);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("pizza");
		setTextureName("wellozmod:pizza");
	}
}