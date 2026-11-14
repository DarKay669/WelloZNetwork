package fr.welloznetwork.wellozmod.modules.hud.mixins.client.gui;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.modules.hud.MHUD;
import fr.welloznetwork.wellozmod.modules.hud.init.RegistrySettings;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

@Mixin(GuiScreen.class)
public class MixinGuiScreen
{
    @Shadow
    public int width;
    
    @Shadow
    public int height;
    
    @Inject(method = "drawWorldBackground", at = @At("HEAD"), cancellable = true)
    private void DrawWorldBackground(int tint, CallbackInfo callbackInfo)
    {
        if (RegistrySettings.blurEnabled)
        {
            int color = MHUD.CLIENT_LISTENER.getBackgroundColor();
            int a = (color >> 24) & 0xFF;
            int r = (color >> 16) & 0xFF;
            int b = (color >> 8) & 0xFF;
            int g = color & 0xFF;
            
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            
            Tessellator tessellator = Tessellator.instance;
            
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(r, g, b, a);
            tessellator.addVertex(0.0D, this.height, 0.0D);
            tessellator.addVertex(this.width, this.height, 0.0D);
            tessellator.addVertex(this.width, 0.0D, 0.0D);
            tessellator.addVertex(0.0D, 0.0D, 0.0D);
            tessellator.draw();
            
            GL11.glEnable(GL11.GL_TEXTURE_2D);;
            
            callbackInfo.cancel();
        }
    }
}