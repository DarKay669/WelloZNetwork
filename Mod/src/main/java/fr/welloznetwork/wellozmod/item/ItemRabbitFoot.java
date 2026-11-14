package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemRabbitFoot extends Item
{
	public ItemRabbitFoot()
	{
		setUnlocalizedName("rabbit_foot");
		setTextureName("wellozmod:rabbit_foot");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
}