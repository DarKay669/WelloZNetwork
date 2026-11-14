package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemPrismarineShard extends Item
{
	public ItemPrismarineShard()
	{
		setUnlocalizedName("prismarine_shard");
		setTextureName("wellozmod:prismarine_shard");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
}