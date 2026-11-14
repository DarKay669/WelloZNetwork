package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.item.ItemAxe;

public class ItemCandyCaneAxe extends ItemAxe
{
    public ItemCandyCaneAxe()
    {
        super(RegistryToolMaterials.CANDY_CANE);
        setUnlocalizedName("candy_cane_axe");
        setTextureName("wellozmod:candy_cane_axe");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
}