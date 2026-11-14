package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemPrismarineCrystals extends Item
{
	public ItemPrismarineCrystals()
	{
		setTextureName("wellozmod:prismarine_crystals");
		setUnlocalizedName("prismarine_crystals");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
}