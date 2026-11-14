package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemVanillaCone extends ItemFood
{
    public ItemVanillaCone()
    {
        super(6, 0.0F, false);
        setUnlocalizedName("vanilla_cone");
        setTextureName("wellozmod:vanilla_cone");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}