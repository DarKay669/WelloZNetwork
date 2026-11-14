package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemPoppedChorusFruit extends Item
{
    public ItemPoppedChorusFruit()
    {
        setUnlocalizedName("popped_chorus_fruit");
        setTextureName("wellozmod:popped_chorus_fruit");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}