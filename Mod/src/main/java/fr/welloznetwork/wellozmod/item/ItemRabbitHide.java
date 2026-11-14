package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemRabbitHide extends Item
{
	public ItemRabbitHide()
	{
		setUnlocalizedName("rabbit_hide");
		setTextureName("wellozmod:rabbit_hide");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
}