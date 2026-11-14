package fr.welloznetwork.wellozmod.modules.hud.mixins.client.gui;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import fr.welloznetwork.venus.lib.render.ButtonTheme;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

@Mixin(GuiButton.class)
public class MixinGuiButton extends Gui
{
    @Shadow
    public int width;
    
    @Shadow
    public int height;
    
    @Shadow
    public int xPosition;
    
    @Shadow
    public int yPosition;
    
    @Shadow
    public String displayString;
    
    @Shadow
    public boolean enabled;
    
    @Shadow
    public boolean visible;
    
    @Shadow
    protected boolean field_146123_n;
    
    private static final ResourceLocation OLD_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation NEW_TEXTURES = new ResourceLocation("wellozmod:textures/gui/new_widgets.png");
    private static final ResourceLocation LEGACY_TEXTURES = new ResourceLocation("wellozmod:textures/gui/legacy_widgets.png");
    
    @Overwrite
    public int getHoverState(boolean mouseOver)
    {
        int i = 1;
        
        if (!this.enabled)
        {
            i = 0;
        }
        
        else if (mouseOver)
        {
            i = 2;
        }
        
        return i;
    }
    
    @Overwrite
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            if (RegistrySettings.activeTheme == ButtonTheme.OLD)
            {
                mc.getTextureManager().bindTexture(OLD_TEXTURES);
                this.drawButtonTheme(mc, mouseX, mouseY);
            }
            
            if (RegistrySettings.activeTheme == ButtonTheme.NEW)
            {
                mc.getTextureManager().bindTexture(NEW_TEXTURES);
                this.drawButtonTheme(mc, mouseX, mouseY);
            }
            
            if (RegistrySettings.activeTheme == ButtonTheme.LEGACY)
            {
                mc.getTextureManager().bindTexture(LEGACY_TEXTURES);
                this.drawButtonTheme(mc, mouseX, mouseY);
            }
        }
    }
    
    @Overwrite
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
    
    private void drawButtonTheme(Minecraft mc, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        
        int i = this.getHoverState(this.field_146123_n);
        
        GL11.glEnable(GL11.GL_BLEND);
        
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        
        this.mouseDragged(mc, mouseX, mouseY);
        
        int j = 14737632;
        
        if (!this.enabled)
        {
            j = 10526880;
        }
        
        else if (this.field_146123_n)
        {
            j = 16777120;
        }
        
        this.drawCenteredString(mc.fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
    }
}