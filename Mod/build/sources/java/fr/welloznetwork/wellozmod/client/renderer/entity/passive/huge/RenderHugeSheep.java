package fr.welloznetwork.wellozmod.client.renderer.entity.passive.huge;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.passive.huge.EntityHugeSheep;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderHugeSheep extends RenderLiving
{
    private static final ResourceLocation sheepTextures = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");
    private float scale;
    
    public RenderHugeSheep(ModelBase modelBase, ModelBase modelBase1, float x, float y)
    {
        super(modelBase, x * y);
        this.setRenderPassModel(modelBase1);
        this.scale = y;
    }
    
    protected void preRenderCallback(EntityHugeSheep entityHugeSheep, float scale)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }
    
    protected int shouldRenderPass(EntityHugeSheep entityHugeSheep, int render, float pass)
    {
        if (render == 0 && !entityHugeSheep.getSheared())
        {
            this.bindTexture(sheepTextures);
            
            if (entityHugeSheep.hasCustomNameTag() && "jeb_".equals(entityHugeSheep.getCustomNameTag()))
            {
                boolean flag = true;
                int k = entityHugeSheep.ticksExisted / 25 + entityHugeSheep.getEntityId();
                int l = k % EntityHugeSheep.fleeceColorTable.length;
                int i1 = (k + 1) % EntityHugeSheep.fleeceColorTable.length;
                float f1 = ((float) (entityHugeSheep.ticksExisted % 25) + pass) / 25.0F;
                GL11.glColor3f(EntityHugeSheep.fleeceColorTable[l][0] * (1.0F - f1) + EntityHugeSheep.fleeceColorTable[i1][0] * f1, EntityHugeSheep.fleeceColorTable[l][1] * (1.0F - f1) + EntityHugeSheep.fleeceColorTable[i1][1] * f1, EntityHugeSheep.fleeceColorTable[l][2] * (1.0F - f1) + EntityHugeSheep.fleeceColorTable[i1][2] * f1);
            }
            
            else
            {
                int j = entityHugeSheep.getFleeceColor();
                GL11.glColor3f(EntityHugeSheep.fleeceColorTable[j][0], EntityHugeSheep.fleeceColorTable[j][1], EntityHugeSheep.fleeceColorTable[j][2]);
            }
            
            return 1;
        }
        
        else
        {
            return -1;
        }
    }
    
    protected ResourceLocation getEntityTexture(EntityHugeSheep entityHugeSheep)
    {
        return shearedSheepTextures;
    }
    
    protected int shouldRenderPass(EntityLivingBase entityLivingBase, int render, float pass)
    {
        return this.shouldRenderPass((EntityHugeSheep) entityLivingBase, render, pass);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityHugeSheep) entity);
    }
}