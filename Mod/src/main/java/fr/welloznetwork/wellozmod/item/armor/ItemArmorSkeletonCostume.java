package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorSkeletonCostume extends ItemArmor
{
    public ItemArmorSkeletonCostume(String unlocalizedName, int armorType)
    {
        super(RegistryArmorMaterials.COSTUME, 0, armorType);
        setUnlocalizedName(unlocalizedName);
        setTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_HALLOWEEN);
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String armorType)
    {
        if (itemStack.getItem().equals(RegistryItems.skeleton_chestplate) || itemStack.getItem().equals(RegistryItems.skeleton_leggings))
        {
            return "wellozmod:textures/models/armors/skeleton_1.png";
        }
        
        else if (itemStack.getItem().equals(RegistryItems.skeleton_boots))
        {
            return "wellozmod:textures/models/armors/skeleton_2.png";
        }
        
        return null;
    }
}