package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemSoup;

public class ItemRabbitStew extends ItemSoup
{
    public ItemRabbitStew()
    {
        super(10);
        setUnlocalizedName("rabbit_stew");
        setTextureName("wellozmod:rabbit_stew");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}