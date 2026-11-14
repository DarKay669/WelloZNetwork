package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.ItemBucket;

public class ItemPoisonWaterBucket extends ItemBucket
{
    public ItemPoisonWaterBucket()
    {
        super(RegistryBlocks.poison_water);
        setMaxStackSize(1);
        setMaxDamage(1);
        setUnlocalizedName("poison_water_bucket");
        setTextureName("wellozmod:poison_water_bucket");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
}