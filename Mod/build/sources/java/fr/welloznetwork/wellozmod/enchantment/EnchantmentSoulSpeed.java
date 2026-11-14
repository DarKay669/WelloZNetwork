package fr.welloznetwork.wellozmod.enchantment;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class EnchantmentSoulSpeed extends Enchantment
{
    public EnchantmentSoulSpeed()
    {
        super(72, 1, EnumEnchantmentType.armor_feet);
        setName("soul_speed");
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 10;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel + 15;
    }
    
    public int getMaxLevel()
    {
        return 3;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack itemStack)
    {
        return itemStack != null && itemStack.getItem() == Items.book; 
    }
    
    public static void getSoulSpeedModifier(EntityLivingBase entityLivingBase)
    {
        Block block = entityLivingBase.worldObj.getBlock(MathHelper.floor_double(entityLivingBase.posX), MathHelper.floor_double(entityLivingBase.boundingBox.minY - 0.01D), MathHelper.floor_double(entityLivingBase.posZ));
        
        if (block == Blocks.soul_sand || block == RegistryBlocks.soul_soil)
        {
            int level = CustomEnchantmentHelper.getSoulSpeedModifier(entityLivingBase);
            
            if (level > 0)
            {
                entityLivingBase.motionX *= 1.0F + (0.1F + 0.1F * level);
                entityLivingBase.motionZ *= 1.0F + (0.1F + 0.1F * level);
                
                for (int i = 0; i < 5; i++)
                {
                    //entityLivingBase.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.soul_sand) + "_0", entityLivingBase.posX + (entityLivingBase.worldObj.rand.nextDouble() - 0.5D) * entityLivingBase.width, entityLivingBase.posY + 0.1D, entityLivingBase.posZ + (entityLivingBase.worldObj.rand.nextDouble() - 0.5D) * entityLivingBase.width, 0.0D, 0.0D, 0.0D);
                }
                
                if (entityLivingBase instanceof EntityPlayer && entityLivingBase.worldObj.rand.nextFloat() < 0.04F)
                {
                    ItemStack itemStack = ((EntityPlayer) entityLivingBase).getEquipmentInSlot(1);
                    
                    if (itemStack != null)
                    {
                        itemStack.damageItem(1, entityLivingBase);
                    }
                }
            }
        }
    }
}