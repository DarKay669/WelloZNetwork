package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemElytra extends Item
{
    public ItemElytra()
    {
        setMaxDamage(432);
        setMaxStackSize(1);
        setUnlocalizedName("elytra");
        setTextureName("wellozmod:elytra");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, BlockDispenser.dispenseBehaviorRegistry.getObject(Items.iron_chestplate));
    }
    
    public static boolean isBroken(ItemStack itemStack)
    {
        return itemStack.getItemDamage() < itemStack.getMaxDamage() - 1;
    }
    
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Items.leather;
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (getElytra(entityPlayer) != null)
        {
            return itemStack;
        }
        
        if (itemStack.stackSize > 0 && getElytra(entityPlayer) == null)
        {
            if (entityPlayer.getEquipmentInSlot(3) == null)
            {
                entityPlayer.setCurrentItemOrArmor(3, itemStack.copy());
                
                if (!entityPlayer.capabilities.isCreativeMode)
                {
                    itemStack.stackSize = 0;
                }
                
                world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_ARMOR_EQUIP_GENERIC[world.rand.nextInt(RegistrySounds.ITEM_ARMOR_EQUIP_GENERIC.length)], 1.0F, 1.0F);
                
                return itemStack;
            }
        }
        
        return itemStack;
    }
    
    public boolean isValidArmor(ItemStack itemStack, int armorType, Entity entity)
    {
        return armorType == 1 && entity instanceof EntityLivingBase && getElytra((EntityLivingBase) entity) == null;
    }
    
    public static ItemStack getElytra(EntityLivingBase entityLivingBase)
    {
        ItemStack itemStack = entityLivingBase.getEquipmentInSlot(3);
        
        if (itemStack != null && itemStack.getItem() instanceof ItemElytra)
        {
            return itemStack;
        }
        
        return null;
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.epic;
    }
}