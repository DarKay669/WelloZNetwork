package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDiscFragment5 extends Item
{
    public ItemDiscFragment5()
    {
        setUnlocalizedName("disc_fragment_5");
        setTextureName("wellozmod:disc_fragment_5");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.uncommon;
    }
}