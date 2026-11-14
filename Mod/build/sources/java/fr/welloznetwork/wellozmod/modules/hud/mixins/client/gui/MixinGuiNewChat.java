package fr.welloznetwork.wellozmod.modules.hud.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.welloznetwork.venus.lib.dev.DeveloperHelper;
import fr.welloznetwork.venus.lib.render.ChatMode;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat extends Gui
{
    @DeveloperHelper("The adaptive chat mode WIP")
    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
    private void newDrawChat(int left, int top, int right, int bottom, int color)
    {
        if (RegistrySettings.chatMode == ChatMode.NORMAL)
        {
            drawRect(left, top, right, bottom, color);
        }
        
        if (RegistrySettings.chatMode == ChatMode.ADAPTIVE)
        {
            drawRect(left, top, right, bottom, color);
        }
        
        if (RegistrySettings.chatMode == ChatMode.CLEAR)
        {
            drawRect(left, top, right, bottom, 0x00000000);
        }
    }
}