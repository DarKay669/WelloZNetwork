package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemShark extends ItemFood 
{
	public ItemShark() 
	{
		super(8, true);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("shark");
		setTextureName("wellozmod:shark");
	}
}