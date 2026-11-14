package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDragonBreath extends Item
{
	public ItemDragonBreath()
	{
		setPotionEffect("-14+13");
		setUnlocalizedName("dragon_breath");
		setContainerItem(Items.glass_bottle);
		setTextureName("wellozmod:dragon_breath");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.uncommon;
    }
}