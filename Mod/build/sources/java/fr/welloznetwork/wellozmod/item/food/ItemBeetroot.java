package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemBeetroot extends ItemFood
{
	public ItemBeetroot()
	{
		super(1, 0.6F, false);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("beetroot");
		setTextureName("wellozmod:beetroot");
	}
}