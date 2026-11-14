package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCookedVenison extends ItemFood
{
    public ItemCookedVenison()
    {
        super(10, 0.9F, true);
        setUnlocalizedName("cooked_venison");
        setTextureName("wellozmod:cooked_venison");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}