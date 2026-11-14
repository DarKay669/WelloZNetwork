package fr.welloznetwork.wellozmod.item.weapon;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemNetheriteSword extends ItemSword
{
	public ItemNetheriteSword()
	{
		super(RegistryToolMaterials.NETHERITE);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setUnlocalizedName("netherite_sword");
		setTextureName("wellozmod:netherite_sword");
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