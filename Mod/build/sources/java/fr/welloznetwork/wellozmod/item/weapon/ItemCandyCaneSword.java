package fr.welloznetwork.wellozmod.item.weapon;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemCandyCaneSword extends ItemSword
{
    public ItemCandyCaneSword()
    {
        super(RegistryToolMaterials.CANDY_CANE);
        setUnlocalizedName("candy_cane_sword");
        setTextureName("wellozmod:candy_cane_sword");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase1)
    {
        if (!entityLivingBase1.worldObj.isRemote && entityLivingBase1.isSneaking())
        {
            entityLivingBase1.dropItem(RegistryItems.candy_cane, 1);
            itemStack.damageItem(6, entityLivingBase1);
            return true;
        }
        
        itemStack.damageItem(1, entityLivingBase1);
        return true;
    }
}