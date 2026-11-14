package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorEmerald extends ItemArmor
{
	public ItemArmorEmerald(String unlocalizedName, int type)
	{
		super(RegistryArmorMaterials.EMERALD, 0, type);
		setUnlocalizedName(unlocalizedName);
		setTextureName("wellozmod:" + unlocalizedName);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
	{
		if (slot == 2)
		{
			return "wellozmod:textures/models/armors/emerald_2.png";
		}
		
		return "wellozmod:textures/models/armors/emerald_1.png";
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