package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemNetheriteShovel extends ItemSpade
{
	public ItemNetheriteShovel()
	{
		super(RegistryToolMaterials.NETHERITE);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:netherite_shovel");
		setUnlocalizedName("netherite_shovel");
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
	    if (repair.getItem() == RegistryItems.netherite && repair.getItemDamage() == 0)
		{
			return true;
		}
		
		return false;
	}
}