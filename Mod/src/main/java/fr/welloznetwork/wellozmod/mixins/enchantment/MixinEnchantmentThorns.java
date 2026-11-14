package fr.welloznetwork.wellozmod.mixins.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

@Mixin(EnchantmentThorns.class)
public class MixinEnchantmentThorns extends Enchantment
{
    protected MixinEnchantmentThorns(int id, int weight, EnumEnchantmentType enumEnchantmentType)
    {
        super(id, weight, enumEnchantmentType);
    }
    
    @Overwrite
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }
    
    @Overwrite
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel);
    }
    
    @Overwrite
    public int getMaxLevel()
    {
        return 3;
    }
    
    @Overwrite
    public void func_151367_b(EntityLivingBase entityLivingBase, Entity entity, int enchantmentLevel)
    {
        if (entity != null)
        {
            if (entityLivingBase.worldObj.rand.nextFloat() < 0.15F * (float) enchantmentLevel)
            {
                int damage = 1 + entityLivingBase.worldObj.rand.nextInt(4);
                
                if (enchantmentLevel > 0)
                {
                    damage += entityLivingBase.worldObj.rand.nextInt(enchantmentLevel + 1);
                }
                
                entity.attackEntityFrom(DamageSource.causeThornsDamage(entityLivingBase), (float) damage);
                
                if (entityLivingBase instanceof EntityPlayer)
                {
                    for (ItemStack itemStack : ((EntityPlayer) entityLivingBase).inventory.armorInventory)
                    {
                        if (itemStack != null && EnchantmentHelper.getEnchantmentLevel(this.effectId, itemStack) > 0)
                        {
                            itemStack.damageItem(2, entityLivingBase);
                            break;
                        }
                    }
                }
            }
        }
    }
}