package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorSanta extends ItemArmor
{
    public ItemArmorSanta(String unlocalizedName, int type)
    {
        super(RegistryArmorMaterials.SANTA, 0, type);
        setUnlocalizedName(unlocalizedName);
        setTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
    {
        if (slot == 2)
        {
            return "wellozmod:textures/models/armors/santa_2.png";
        }
        
        return "wellozmod:textures/models/armors/santa_1.png";
    }
}