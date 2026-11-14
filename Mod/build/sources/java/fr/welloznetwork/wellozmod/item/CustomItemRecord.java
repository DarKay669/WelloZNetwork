package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Custom(ItemRecord.class)
public class CustomItemRecord extends ItemRecord
{    
    public CustomItemRecord(String recordName, String unlocalizedName)
    {
        super(recordName);
        setUnlocalizedName(unlocalizedName);
        setTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public ResourceLocation getRecordResource(String recordName)
    {
        return new ResourceLocation(this.recordName);
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.rare;
    }
}