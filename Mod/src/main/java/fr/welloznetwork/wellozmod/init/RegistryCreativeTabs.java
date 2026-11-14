package fr.welloznetwork.wellozmod.init;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.enchantment.CustomEnchantmentHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class RegistryCreativeTabs
{
    public static final CreativeTabs TAB_WELLOZ = new CreativeTabs(12, "wellozmod")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(RegistryBlocks.emerald_chest);
        }
        
        @SideOnly(Side.CLIENT)
        public void displayAllReleventItems(List list)
        {
            if (RegistryEnchantments.DEATH_BRINGER != null)
            {
                CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.DEATH_BRINGER, list);
            }
            
            if (RegistryEnchantments.EXPLOSIVE != null)
            {
                CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.EXPLOSIVE, list);
            }
            
            if (RegistryEnchantments.FROST_WALKER != null)
            {
                CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.FROST_WALKER, list);
            }
            
            if (RegistryEnchantments.IMPLANTS != null)
            {
                CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.IMPLANTS, list);
            }
            
            if (RegistryEnchantments.LAST_STAND != null)
            {
                CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.LAST_STAND, list);
            }
            
            if (RegistryEnchantments.MENDING != null)
            {
                CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.MENDING, list);
            }
            
            if (RegistryEnchantments.WITHER_HUNTER != null)
            {
                CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.WITHER_HUNTER, list);
            }
            
            super.displayAllReleventItems(list);
        }
    };
    
    public static final CreativeTabs TAB_WELLOZ_HALLOWEEN = new CreativeTabs(13, "wellozmod_halloween")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(RegistryBlocks.pumpkin_face_1_off);
        }
    };
    
    public static final CreativeTabs TAB_WELLOZ_CHRISTMAS = new CreativeTabs(14, "wellozmod_christmas")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return RegistryItems.santa_hat;
        }
    };
    
    public static final CreativeTabs TAB_WELLOZ_V2 = new CreativeTabs(15, "V2")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return RegistryItems.cherry_boat;
        }
        
        @SideOnly(Side.CLIENT)
        public void displayAllReleventItems(List list)
        {
            if (Constants.V2)
            {
                if (RegistryEnchantments.DEPTH_STRIDER != null)
                {
                    CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.DEPTH_STRIDER, list);
                }
                
                if (RegistryEnchantments.SWIFT_SNEAK != null)
                {
                    CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.SWIFT_SNEAK, list);
                }
                
                if (RegistryEnchantments.SOUL_SPEED != null)
                {
                    CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.SOUL_SPEED, list);
                }
                
                if (RegistryEnchantments.BINDING_CURSE != null)
                {
                    CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.BINDING_CURSE, list);
                }
                
                if (RegistryEnchantments.SWEEPING_EDGE != null)
                {
                    CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.SWEEPING_EDGE, list);
                }
                
                if (RegistryEnchantments.VANISHING_CURSE != null)
                {
                    CustomEnchantmentHelper.addAllBooks(RegistryEnchantments.VANISHING_CURSE, list);
                }
            }
        }
    };
}