package fr.welloznetwork.wellozmod.mixins.potion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionAttackDamage;

@Mixin(PotionAttackDamage.class)
public class MixinPotionAttackDamage extends Potion
{
    public MixinPotionAttackDamage(int id, boolean isBadEffect, int color)
    {
        super(id, isBadEffect, color);
    }
    
    @Overwrite
    public double func_111183_a(int level, AttributeModifier attributeModifier)
    {
        return (this.id == Potion.weakness.id) ? (-0.5F * (level + 1)) : (0.4D * (level + 1));
    }
}