package fr.welloznetwork.wellozmod.client.renderer.entity.projectile;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.projectile.EntitySplashPotion;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderEntitySplashPotion extends Render
{
    private static ResourceLocation NAUSEA = new ResourceLocation("wellozmod:textures/items/sickness_splash_potion.png");
    private static ResourceLocation WEB = new ResourceLocation("wellozmod:textures/items/web_splash_potion.png");
    private static ResourceLocation EXPLOSIVE = new ResourceLocation("wellozmod:textures/items/explosive_splash_potion.png");
    private static ResourceLocation DARKNESS = new ResourceLocation("wellozmod:textures/items/darkness_splash_potion.png");
    
    private float scale = 1.0F;
    
    public void renderProjectile(EntityThrowable projectile, double x, double y, double z)
    {
        GL11.glPushMatrix();
        bindEntityTexture((Entity) projectile);
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glEnable(32826);
        GL11.glScalef(this.scale * 0.5F, this.scale * 0.5F, this.scale * 0.5F);
        Tessellator tes = Tessellator.instance;
        float minU = 0.0F;
        float maxU = 1.0F;
        float minV = 0.0F;
        float maxV = 1.0F;
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 1.0F, 0.0F);
        tes.addVertexWithUV((0.0F - f8), (0.0F - f9), 0.0D, minU, maxV);
        tes.addVertexWithUV((f7 - f8), (0.0F - f9), 0.0D, maxU, maxV);
        tes.addVertexWithUV((f7 - f8), (1.0F - f9), 0.0D, maxU, minV);
        tes.addVertexWithUV((0.0F - f8), (1.0F - f9), 0.0D, minU, minV);
        tes.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderProjectile((EntityThrowable) par1Entity, par2, par4, par6);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        EntitySplashPotion entitySplashPotion = (EntitySplashPotion) entity;
        
        switch (entitySplashPotion.getDamageValue()) 
        {
            case 0:
                return NAUSEA;
                
            case 1:
                return WEB;
                
            case 2:
                return EXPLOSIVE;
                
            case 3:
                return DARKNESS;
        }
        
        return NAUSEA;
    }
}