package fr.welloznetwork.wellozmod.client.renderer;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@Custom(InventoryEffectRenderer.class)
@SideOnly(Side.CLIENT)
public abstract class CustomInventoryEffectRenderer extends GuiContainer
{
    private boolean hasActivePotionEffects;
    
    public CustomInventoryEffectRenderer(Container inventorySlots)
    {
        super(inventorySlots);
    }
    
    public void initGui()
    {
        super.initGui();
        this.updateActivePotionEffects();
    }
    
    protected void updateActivePotionEffects()
    {
        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty())
        {
            this.guiLeft = 100 + (this.width - this.xSize - 200) / 2;
            this.hasActivePotionEffects = true;
        }
        
        else
        {
            this.guiLeft = (this.width - this.xSize) / 2;
            this.hasActivePotionEffects = false;
        }
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        if (this.hasActivePotionEffects)
        {
            this.drawActivePotionEffects();
        }
    }
    
    private void drawActivePotionEffects()
    {
        int i = this.guiLeft - 124;
        int j = this.guiTop;
        int k = 166;
        
        Collection<PotionEffect> collection = this.mc.thePlayer.getActivePotionEffects();
        
        if (!collection.isEmpty())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            
            int l = 33;
            
            if (collection.size() > 5)
            {
                l = 132 / (collection.size() - 1);
            }
            
            for (Iterator iterator = this.mc.thePlayer.getActivePotionEffects().iterator(); iterator.hasNext(); j += k)
            {
                PotionEffect potionEffect = (PotionEffect) iterator.next();
                Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                
                this.mc.getTextureManager().bindTexture(field_147001_a);
                this.drawTexturedModalRect(i, j, 0, 166, 140, 32);
                
                if (potion.hasStatusIcon())
                {
                    int i1 = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(i + 6, j + 7, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }
                
                String s1 = I18n.format(potion.getName());
                
                if (potionEffect.getAmplifier() == 1)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.2");
                }
                
                else if (potionEffect.getAmplifier() == 2)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.3");
                }
                
                else if (potionEffect.getAmplifier() == 3)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.4");
                }
                
                this.fontRendererObj.drawStringWithShadow(s1, i + 10 + 18, j + 6, Color.WHITE.getRGB());
                String s = Potion.getDurationString(potionEffect);
                this.fontRendererObj.drawStringWithShadow(s, i + 10 + 18, j + 6 + 10, Color.LIGHT_GRAY.getRGB());
            }
        }
    }
}