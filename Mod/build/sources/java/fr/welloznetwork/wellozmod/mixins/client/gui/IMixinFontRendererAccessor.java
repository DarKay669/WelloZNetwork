package fr.welloznetwork.wellozmod.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

@Mixin(FontRenderer.class)
public interface IMixinFontRendererAccessor
{
    @Accessor("locationFontTexture")
    void setResourceLocation(ResourceLocation paramResourceLocation);
}