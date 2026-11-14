package fr.welloznetwork.wellozmod.client.ui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UISpyglass extends Gui
{
    public static final UISpyglass INSTANCE = new UISpyglass();

    public void draw()
    {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);

        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("wellozmod:textures/gui/spyglass_overlay.png"));

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glScalef((float)scaledResolution.getScaledWidth_double() / 256.0F, (float)scaledResolution.getScaledHeight_double() / 256.0F, 0.0F);
        this.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
        GL11.glScalef(1.0F / (float)scaledResolution.getScaledWidth_double() / 256.0F, 1.0F / (float)scaledResolution.getScaledHeight_double() / 256.0F, 0.0F);
    }
}