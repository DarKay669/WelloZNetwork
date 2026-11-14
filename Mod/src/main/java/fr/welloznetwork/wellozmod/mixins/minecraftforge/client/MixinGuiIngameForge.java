package fr.welloznetwork.wellozmod.mixins.minecraftforge.client;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import fr.welloznetwork.wellozmod.client.ui.CustomFontRenderer;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.client.GuiIngameForge;

@Mixin(GuiIngameForge.class)
public class MixinGuiIngameForge extends GuiIngame
{
    public MixinGuiIngameForge(Minecraft mc)
    {
        super(mc);
    }
    
    @Overwrite
    protected void renderToolHightlight(int width, int height)
    {
        if (this.mc.gameSettings.heldItemTooltips && !CommandSpectator.isSpectator())
        {
            this.mc.mcProfiler.startSection("toolHighlight");
            
            if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null)
            {
                String name = this.highlightingItemStack.getDisplayName();
                
                int opacity = (int) ((float) this.remainingHighlightTicks * 256.0F / 10.0F);
                
                if (opacity > 255)
                {
                    opacity = 255;
                }
                
                if (opacity > 0)
                {
                    int y = height - 59;
                    
                    if (!this.mc.playerController.shouldDrawHUD())
                    {
                        y += 14;
                    }
                    
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    CustomFontRenderer pixelNES = CustomFontRenderer.initTTF("Pixel-NES.otf", 15);
                    
                    pixelNES.drawStringWithShadow(name, (width - pixelNES.getStringWidth(name)) / 2, y, Color.WHITE.getRGB() | (opacity << 24));
                    
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
            
            this.mc.mcProfiler.endSection();
        }
    }
}