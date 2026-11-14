package fr.welloznetwork.wellozmod.mixins.client.gui;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

@Mixin(GuiScreen.class)
public class MixinGuiScreen extends Gui
{
    @Shadow
    public Minecraft mc;
    
    @Overwrite
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (keyCode == 1)
        {
            this.mc.displayGuiScreen(null);
            
            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        }
    }
    
    @Overwrite
    public void handleKeyboardInput()
    {
        char c0 = Keyboard.getEventCharacter();
        
        if (Keyboard.getEventKey() == c0 && c0 >= ' ' || Keyboard.getEventKeyState())
        {
            this.keyTyped(c0, Keyboard.getEventKey());
        }
        
        this.mc.func_152348_aa();
    }
}