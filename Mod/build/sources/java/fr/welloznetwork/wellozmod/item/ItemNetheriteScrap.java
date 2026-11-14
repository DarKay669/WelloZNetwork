package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.Item;

public class ItemNetheriteScrap extends Item
{
    public ItemNetheriteScrap()
    {
        setUnlocalizedName("netherite_scrap");
        setTextureName("wellozmod:netherite_scrap");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}