package fr.welloznetwork.wellozmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;

public class EnchantmentDepthStrider extends Enchantment
{
    public EnchantmentDepthStrider()
    {
        super(70, 2, EnumEnchantmentType.armor_feet);
        setName("depth_strider");
    }
    
    public int getMinEnchantability(int enchantmentLevel)
    {
        return enchantmentLevel * 10;
    }
    
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }
    
    public int getMaxLevel()
    {
        return 3;
    }
    
    public static void getDepthStriderModifier(EntityLivingBase entityLivingBase)
    {
        double d0 = entityLivingBase.posY;
        float f1 = 0.8F;
        float f2 = 0.02F;
        float f3 = (float) CustomEnchantmentHelper.getDepthStriderModifier(entityLivingBase);
        
        if (f3 > 3.0F)
        {
            f3 = 3.0F;
        }
        
        if (!entityLivingBase.onGround)
        {
            f3 *= 0.5F;
        }
        
        if (f3 > 0.0F)
        {
            f1 += (0.54600006F - f1) * f3 / 3.0F;
            f2 += (entityLivingBase.getAIMoveSpeed() - f2) * f3 / 3.0F;
        }
        
        entityLivingBase.moveFlying(entityLivingBase.moveStrafing, entityLivingBase.moveForward, f2);
        entityLivingBase.moveEntity(entityLivingBase.motionX, entityLivingBase.motionY, entityLivingBase.motionZ);
        entityLivingBase.motionX *= (double) f1;
        entityLivingBase.motionY *= 0.800000011920929D;
        entityLivingBase.motionZ *= (double )f1;
        
        entityLivingBase.motionY -= 0.02D;
        
        if (entityLivingBase.isCollidedHorizontally && entityLivingBase.isOffsetPositionInLiquid(entityLivingBase.motionX, entityLivingBase.motionY + 0.6000000238418579D - entityLivingBase.posY + d0, entityLivingBase.motionZ))
        {
            entityLivingBase.motionY = 0.30000001192092896D;
        }
    }
}