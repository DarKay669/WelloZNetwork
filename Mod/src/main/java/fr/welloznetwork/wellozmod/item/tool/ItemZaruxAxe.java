package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemZaruxAxe extends ItemAxe
{
	public ItemZaruxAxe()
	{
		super(RegistryToolMaterials.ZARUX);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:zarux_axe");
		setUnlocalizedName("zarux_axe");
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