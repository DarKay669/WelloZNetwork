package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemGingerbreadMan extends ItemFood
{
    public ItemGingerbreadMan()
    {
        super(3, 0.3F, false);
        setUnlocalizedName("gingerbread_man");
        setTextureName("wellozmod:gingerbread_man");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}