package fr.welloznetwork.wellozmod.client.renderer.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.block.EntityTNTPresent;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class BlockTNTPresentRenderer extends Render
{
    private RenderBlocks renderBlocks = new RenderBlocks();
    
    public BlockTNTPresentRenderer()
    {
        this.shadowSize = 0.5F;
    }
    
    public void doRender(EntityTNTPresent entityTNTPresent, double x, double y, double z, float entityYaw, float partialTicks)
    {  
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y + 0.48F, (float) z);
        
        float f2;
        
        if ((float) entityTNTPresent.fuse - partialTicks + 1.0F < 10.0F)
        {
            f2 = 1.0F - ((float) entityTNTPresent.fuse - partialTicks + 1.0F) / 10.0F;
            
            if (f2 < 0.0F)
            {
                f2 = 0.0F;
            }
            
            if (f2 > 1.0F)
            {
                f2 = 1.0F;
            }
            
            f2 *= f2;
                        
            GL11.glScalef(1.0F + f2 * 0.3F, 1.0F + f2 * 0.3F, 1.0F + f2 * 0.3F);
        }
        
        f2 = (1.0F - ((float) entityTNTPresent.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
        
        this.bindEntityTexture(entityTNTPresent);
        this.renderBlocks.renderBlockAsItem(RegistryBlocks.present_red, 0, entityTNTPresent.getBrightness(partialTicks));
        
        if (entityTNTPresent.fuse / 5 % 2 == 0)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
            
            this.renderBlocks.renderBlockAsItem(RegistryBlocks.present_red, 0, 1.0F);
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        
        GL11.glPopMatrix();
    }
    
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityTNTPresent) entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TextureMap.locationBlocksTexture;
    }
}