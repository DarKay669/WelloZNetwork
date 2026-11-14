package fr.welloznetwork.wellozmod.potion;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.util.ModUtil.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionWaterJump extends Potion
{    
    public PotionWaterJump()
    {
        super(37, false, Color.cyan.getRGB());
        setIconIndex(0, 0);
        setPotionName("potion.imbueWaterJump");
    }
    
    public boolean isReady(int duration, int amplifier)
    {
        return duration % 20 == 0;
    }
    
    public boolean isInstant()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect potionEffect, Minecraft mc)
    {
        mc.renderEngine.bindTexture(new ResourceLocation("wellozmod:textures/gui/effects/water_jump.png"));
        RenderUtils.drawTexturedQuadFit((x + 5), (y + 5), 20.0D, 20.0D, 1.0D);
    }
}