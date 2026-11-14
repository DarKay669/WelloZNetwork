package fr.welloznetwork.wellozmod.mixins.potion;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHealth;

@Mixin(PotionHealth.class)
public class MixinPotionHealth extends Potion
{
    public MixinPotionHealth(int id, boolean isBadEffect, int color)
    {
        super(id, isBadEffect, color);
    }
    
    public void performEffect(EntityLivingBase entityLivingBase, int level)
    {
        if (this.id == Potion.harm.id)
        {
            return;
        }
        
        super.performEffect(entityLivingBase, level);
    }
    
    public void affectEntity(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase1, int i, double d)
    {
        if (this.id == Potion.harm.id)
        {
            return;
        }
        
        super.affectEntity(entityLivingBase, entityLivingBase1, i, d);
    }
}