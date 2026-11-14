package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemRabbit extends ItemFood
{
	public ItemRabbit()
	{
		super(3, true);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("rabbit");
		setTextureName("wellozmod:rabbit");
	}
}