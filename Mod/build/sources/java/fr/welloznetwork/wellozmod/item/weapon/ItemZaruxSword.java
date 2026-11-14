package fr.welloznetwork.wellozmod.item.weapon;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemZaruxSword extends ItemSword
{
	public ItemZaruxSword()
	{
		super(RegistryToolMaterials.ZARUX);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("zarux_sword");
		setTextureName("wellozmod:zarux_sword");
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
		if (repair.getItem() == RegistryItems.zarux)
		{
			return true;
		}
		
		return false;
	}
}