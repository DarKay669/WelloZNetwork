package fr.welloznetwork.wellozmod.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.GuiScreen;

@Mixin(GuiScreen.class)
public interface IMixinGuiScreenAccessor
{
    @Accessor("width")
    int getWidth();
    
    @Accessor("height")
    int getHeight();
}