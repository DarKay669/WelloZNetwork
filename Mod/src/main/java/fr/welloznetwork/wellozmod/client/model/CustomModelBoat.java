package fr.welloznetwork.wellozmod.client.model;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@Custom(ModelBoat.class)
@SideOnly(Side.CLIENT)
public class CustomModelBoat extends ModelBase
{
    public ModelRenderer[] boatSides = new ModelRenderer[5];
    public ModelRenderer noWater;
    
    public CustomModelBoat()
    {
        this.boatSides[0] = (new ModelRenderer(this, 0, 0)).setTextureSize(128, 64);
        this.boatSides[1] = (new ModelRenderer(this, 0, 19)).setTextureSize(128, 64);
        this.boatSides[2] = (new ModelRenderer(this, 0, 27)).setTextureSize(128, 64);
        this.boatSides[3] = (new ModelRenderer(this, 0, 35)).setTextureSize(128, 64);
        this.boatSides[4] = (new ModelRenderer(this, 0, 43)).setTextureSize(128, 64);
        int i = 32;
        int j = 6;
        int k = 20;
        int l = 4;
        int i1 = 28;
        this.boatSides[0].addBox((float) (-i1 / 2), (float) (-k / 2 + 1), -3.0F, i1, k - 4, 3, 0.0F);
        this.boatSides[0].setRotationPoint(0.0F, (float) (l - 1), 1.0F);
        this.boatSides[1].addBox((float) (-i / 2 + 3), (float) (-j - 1), -1.0F, k - 2, j, 2, 0.0F);
        this.boatSides[1].setRotationPoint((float) (-i / 2 + 1), (float) l, 4.0F);
        this.boatSides[2].addBox((float) (-i / 2 + 8), (float) (-j - 1), -1.0F, k - 4, j, 2, 0.0F);
        this.boatSides[2].setRotationPoint((float) (i / 2 - 1), (float) l, 0.0F);
        this.boatSides[3].addBox((float) (-i / 2 + 2), (float) (-j - 1), -1.0F, i - 4, j, 2, 0.0F);
        this.boatSides[3].setRotationPoint(0.0F, (float) l, (float) (-k / 2 + 1));
        this.boatSides[4].addBox((float) (-i / 2 + 2), (float) (-j - 1), -1.0F, i - 4, j, 2, 0.0F);
        this.boatSides[4].setRotationPoint(0.0F, (float) l, (float) (k / 2 - 1));
        this.boatSides[0].rotateAngleX = ((float) Math.PI / 2F);
        this.boatSides[1].rotateAngleY = ((float) Math.PI * 3F / 2F);
        this.boatSides[2].rotateAngleY = ((float) Math.PI / 2F);
        this.boatSides[3].rotateAngleY = (float) Math.PI;
        this.noWater = (new ModelRenderer(this, 0, 0)).setTextureSize(128, 64);
        this.noWater.addBox((float) (-i1 / 2), (float) (-k / 2 + 1), -3.0F, i1, k - 4, 3, 0.0F);
        this.noWater.setRotationPoint(0.0F, (float) (l - 7), 1.0F);
        this.noWater.rotateAngleX = ((float) Math.PI / 2F);
    }
    
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        
        for (int i = 0; i < 5; i++)
        {
            this.boatSides[i].render(scale);
        }
        
        GL11.glPopMatrix();
    }
    
    public void renderMultipass(Entity entity, float p_187054_2_, float p_187054_3_, float p_187054_4_, float p_187054_5_, float p_187054_6_, float scale)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glColorMask(false, false, false, false);
        this.noWater.render(scale);
        GL11.glColorMask(true, true, true, true);
        GL11.glPopMatrix();
    }
    
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {}
}