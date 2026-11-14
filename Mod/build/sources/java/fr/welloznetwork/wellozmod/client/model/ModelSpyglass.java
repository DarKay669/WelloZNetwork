package fr.welloznetwork.wellozmod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelSpyglass extends ModelBase
{
    private ModelRenderer eyeLid, handle, magnifier;
    
    public ModelSpyglass()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.eyeLid = new ModelRenderer(this, 0, 14);
        this.eyeLid.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1);
        this.eyeLid.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.eyeLid.setTextureSize(64, 32);
        this.setRotation(this.eyeLid, 0.0F, 0.0F, 0.0F);
        
        this.handle = new ModelRenderer(this, 0, 8);
        this.handle.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
        this.handle.setRotationPoint(-0.5F, 17.0F, -0.5F);
        this.handle.setTextureSize(64, 32);
        this.setRotation(this.handle, 0.0F, 0.0F, 0.0F);
        
        this.magnifier = new ModelRenderer(this, 0, 0);
        this.magnifier.addBox(0.0F, 0.0F, 0.0F, 3, 5, 3);
        this.magnifier.setRotationPoint(-1.0F, 12.0F, -1.0F);
        this.magnifier.setTextureSize(64, 32);
        this.magnifier.mirror = true;
        this.setRotation(this.magnifier, 0.0F, 0.0F, 0.0F);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.eyeLid.render(f5);
        this.handle.render(f5);
        this.magnifier.render(f5);
    }
    
    private void setRotation(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}