package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHeartOfTheSea extends Item
{
    public ItemHeartOfTheSea()
    {
        setUnlocalizedName("heart_of_the_sea");
        setTextureName("wellozmod:heart_of_the_sea");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.uncommon;
    }
}