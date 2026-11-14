package fr.welloznetwork.wellozmod.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.EntityLivingBase;

@Mixin(EntityLivingBase.class)
public interface IMixinEntityLivingBaseAccessor
{
    @Accessor("isJumping")
    boolean isJumping();
}