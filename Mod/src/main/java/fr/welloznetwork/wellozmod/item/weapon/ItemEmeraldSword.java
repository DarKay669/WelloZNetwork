package fr.welloznetwork.wellozmod.item.weapon;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemEmeraldSword extends ItemSword
{
	public ItemEmeraldSword()
	{
		super(RegistryToolMaterials.EMERALD);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:emerald_sword");
		setUnlocalizedName("emerald_sword");
	}

	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
		if (repair.getItem() == Items.emerald)
		{
			return true;
		}
		
		return false;
	}
}