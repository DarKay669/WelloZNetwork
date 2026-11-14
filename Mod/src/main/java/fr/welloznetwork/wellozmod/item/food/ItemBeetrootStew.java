package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;

public class ItemBeetrootStew extends ItemSoup
{
    public ItemBeetrootStew()
    {
        super(6);
        setContainerItem(Items.bowl);
        setUnlocalizedName("beetroot_stew");
        setTextureName("wellozmod:beetroot_stew");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}