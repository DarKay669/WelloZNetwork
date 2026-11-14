package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemMutton extends ItemFood
{
	public ItemMutton()
	{
		super(2, true);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("mutton");
		setTextureName("wellozmod:mutton");
	}
}