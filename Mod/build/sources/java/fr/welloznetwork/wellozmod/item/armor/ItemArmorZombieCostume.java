package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorZombieCostume extends ItemArmor
{
    public ItemArmorZombieCostume(String unlocalizedName, int armorType)
    {
        super(RegistryArmorMaterials.COSTUME, 0, armorType);
        setUnlocalizedName(unlocalizedName);
        setTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_HALLOWEEN);
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
    {   
        if (itemStack.getItem().equals(RegistryItems.zombie_chestplate) || itemStack.getItem().equals(RegistryItems.zombie_leggings))
        {
            return "wellozmod:textures/models/armors/zombie_1.png";
        }
                
        else if (itemStack.getItem().equals(RegistryItems.zombie_boots))
        {
            return "wellozmod:textures/models/armors/zombie_2.png";
        }
        
        return null;
    }
}