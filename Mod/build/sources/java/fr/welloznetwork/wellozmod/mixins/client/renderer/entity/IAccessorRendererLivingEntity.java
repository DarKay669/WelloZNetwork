package fr.welloznetwork.wellozmod.mixins.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

@Mixin(RendererLivingEntity.class)
public interface IAccessorRendererLivingEntity
{
    @Accessor("mainModel")
    ModelBase getMainModel();
}