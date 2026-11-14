package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemVenison extends ItemFood
{
    public ItemVenison()
    {
        super(4, 0.3F, true);
        setUnlocalizedName("raw_venison");
        setTextureName("wellozmod:raw_venison");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}