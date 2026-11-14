package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemRawGold extends Item
{
    public ItemRawGold()
    {
        setUnlocalizedName("raw_gold");
        setTextureName("wellozmod:raw_gold");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}