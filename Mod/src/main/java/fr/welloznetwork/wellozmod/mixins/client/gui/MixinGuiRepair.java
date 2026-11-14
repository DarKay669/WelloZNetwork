package fr.welloznetwork.wellozmod.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.gui.GuiRepair;

@Mixin(GuiRepair.class)
public class MixinGuiRepair
{
    @ModifyConstant(method = "drawGuiContainerForegroundLayer", constant = @Constant(intValue = 40))
    private int drawGuiContainerForegroundLayerFix(int constant)
    {
        return Integer.MAX_VALUE;
    }
}