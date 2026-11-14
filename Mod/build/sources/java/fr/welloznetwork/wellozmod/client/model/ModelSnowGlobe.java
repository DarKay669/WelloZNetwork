package fr.welloznetwork.wellozmod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelSnowGlobe extends ModelBase
{
    private ModelRenderer GlobePillar1, GlobePillar2, GlobePillar3, GlobePillar4, GlobeTop1, GlobeTop2, GlobeTop3, GlobeTop4, GlobeFace1, GlobeFace2, GlobeFace3, GlobeFace4, GlobeFace5, GlobeBottom, BaseTop, TreeTrunk, TreeLeaves1, TreeLeaves2, CabinMain, CabinRoof1, CabinRoof2;
    
    public ModelSnowGlobe()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
            
        this.GlobePillar1 = new ModelRenderer(this, 0, 36);
        this.GlobePillar1.addBox(-7.0F, -11.0F, -7.0F, 1, 12, 1);
        this.GlobePillar1.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobePillar1.setTextureSize(128, 64);
        this.GlobePillar1.mirror = true;
        setRotation(this.GlobePillar1, 0.0174533F, 0.0F, 0.0F);
        
        this.GlobePillar2 = new ModelRenderer(this, 4, 36);
        this.GlobePillar2.addBox(6.0F, -11.0F, 6.0F, 1, 12, 1);
        this.GlobePillar2.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobePillar2.setTextureSize(128, 64);
        this.GlobePillar2.mirror = true;
        setRotation(this.GlobePillar2, 0.0174533F, 0.0F, 0.0F);
        
        this.GlobePillar3 = new ModelRenderer(this, 8, 36);
        this.GlobePillar3.addBox(-7.0F, -11.0F, 6.0F, 1, 12, 1);
        this.GlobePillar3.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobePillar3.setTextureSize(128, 64);
        this.GlobePillar3.mirror = true;
        setRotation(this.GlobePillar3, 0.0174533F, 0.0F, 0.0F);
        
        this.GlobePillar4 = new ModelRenderer(this, 12, 36);
        this.GlobePillar4.addBox(6.0F, -11.0F, -7.0F, 1, 12, 1);
        this.GlobePillar4.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobePillar4.setTextureSize(128, 64);
        this.GlobePillar4.mirror = true;
        setRotation(this.GlobePillar4, 0.0174533F, 0.0F, 0.0F);
        
         this.GlobeTop1 = new ModelRenderer(this, 16, 47);
         this.GlobeTop1.addBox(-6.0F, -12.0F, -6.0F, 12, 1, 1);
         this.GlobeTop1.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.GlobeTop1.setTextureSize(128, 64);
         this.GlobeTop1.mirror = true;
         setRotation(this.GlobeTop1, 0.0174533F, 0.0F, 0.0F);
         
         this.GlobeTop2 = new ModelRenderer(this, 56, 51);
         this.GlobeTop2.addBox(-6.0F, -12.0F, -5.0F, 1, 1, 10);
         this.GlobeTop2.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.GlobeTop2.setTextureSize(128, 64);
         this.GlobeTop2.mirror = true;
         setRotation(this.GlobeTop2, 0.0174533F, 0.0F, 0.0F);
         
         this.GlobeTop3 = new ModelRenderer(this, 16, 47);
         this.GlobeTop3.addBox(-6.0F, -12.0F, 5.0F, 12, 1, 1);
         this.GlobeTop3.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.GlobeTop3.setTextureSize(128, 64);
         this.GlobeTop3.mirror = true;
         setRotation(this.GlobeTop3, 0.0174533F, 0.0F, 0.0F);
         
         this.GlobeTop4 = new ModelRenderer(this, 56, 51);
         this.GlobeTop4.addBox(5.0F, -12.0F, -5.0F, 1, 1, 10);
         this.GlobeTop4.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.GlobeTop4.setTextureSize(128, 64);
         this.GlobeTop4.mirror = true;
         setRotation(this.GlobeTop4, 0.0174533F, 0.0F, 0.0F);
         
         this.GlobeFace1 = new ModelRenderer(this, 78, 40);
         this.GlobeFace1.addBox(-6.0F, -11.0F, -6.0F, 0, 12, 12);
         this.GlobeFace1.setRotationPoint(0.0F, 21.0F, 0.0F);
         this.GlobeFace1.setTextureSize(128, 64);
         this.GlobeFace1.mirror = true;
         setRotation(this.GlobeFace1, 0.0174533F, 0.0F, 0.0F);
         
        this.GlobeFace2 = new ModelRenderer(this, 78, 52);
        this.GlobeFace2.addBox(-6.0F, -11.0F, 6.0F, 12, 12, 0);
        this.GlobeFace2.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobeFace2.setTextureSize(128, 64);
        this.GlobeFace2.mirror = true;
        setRotation(this.GlobeFace2, 0.0174533F, 0.0F, 0.0F);
        
        this.GlobeFace3 = new ModelRenderer(this, 78, 40);
        this.GlobeFace3.addBox(6.0F, -11.0F, -6.0F, 0, 12, 12);
        this.GlobeFace3.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobeFace3.setTextureSize(128, 64);
        this.GlobeFace3.mirror = true;
        setRotation(this.GlobeFace3, 0.0174533F, 0.0F, 0.0F);
        
        this.GlobeFace4 = new ModelRenderer(this, 78, 52);
        this.GlobeFace4.addBox(-6.0F, -11.0F, -6.0F, 12, 12, 0);
        this.GlobeFace4.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobeFace4.setTextureSize(128, 64);
        this.GlobeFace4.mirror = true;
        setRotation(this.GlobeFace4, 0.0174533F, 0.0F, 0.0F);
        
        this.GlobeFace5 = new ModelRenderer(this, 93, 42);
        this.GlobeFace5.addBox(-5.0F, -12.0F, -5.0F, 10, 0, 10);
        this.GlobeFace5.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobeFace5.setTextureSize(128, 64);
        this.GlobeFace5.mirror = true;
        setRotation(this.GlobeFace5, 0.0174533F, 0.0174533F, 0.0F);
        
        this.GlobeBottom = new ModelRenderer(this, 0, 49);
        this.GlobeBottom.addBox(-7.0F, 1.0F, -7.0F, 14, 1, 14);
        this.GlobeBottom.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.GlobeBottom.setTextureSize(128, 64);
        this.GlobeBottom.mirror = true;
        setRotation(this.GlobeBottom, 0.0174533F, 0.0174533F, 0.0F);
        
        this.BaseTop = new ModelRenderer(this, 0, 17);
        this.BaseTop.addBox(-7.5F, 1.0F, -7.5F, 15, 1, 15);
        this.BaseTop.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.BaseTop.setTextureSize(128, 64);
        this.BaseTop.mirror = true;
        setRotation(this.BaseTop, 0.0174533F, 0.0F, 0.0F);
        
        this.TreeTrunk = new ModelRenderer(this, 67, 1);
        this.TreeTrunk.addBox(-5.0F, -5.0F, 3.0F, 2, 6, 2);
        this.TreeTrunk.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.TreeTrunk.setTextureSize(128, 64);
        this.TreeTrunk.mirror = true;
        setRotation(this.TreeTrunk, 0.0174533F, 0.0174533F, 0.0F);
        
        this.TreeLeaves1 = new ModelRenderer(this, 67, 10);
        this.TreeLeaves1.addBox(-6.0F, -6.0F, 2.0F, 4, 1, 4);
        this.TreeLeaves1.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.TreeLeaves1.setTextureSize(128, 64);
        this.TreeLeaves1.mirror = true;
        setRotation(this.TreeLeaves1, 0.0174533F, 0.0174533F, 0.0F);
        
        this.TreeLeaves2 = new ModelRenderer(this, 67, 16);
        this.TreeLeaves2.addBox(-5.0F, -7.0F, 3.0F, 2, 1, 2);
        this.TreeLeaves2.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.TreeLeaves2.setTextureSize(128, 64);
        this.TreeLeaves2.mirror = true;
        setRotation(this.TreeLeaves2, 0.0174533F, 0.0174533F, 0.0F);
        
        this.CabinMain = new ModelRenderer(this, 67, 22);
        this.CabinMain.addBox(-1.0F, -3.0F, -5.0F, 6, 4, 6);
        this.CabinMain.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.CabinMain.setTextureSize(128, 64);
        this.CabinMain.mirror = true;
        setRotation(this.CabinMain, 0.0174533F, 0.0174533F, 0.0F);
        
        this.CabinRoof1 = new ModelRenderer(this, 92, 25);
        this.CabinRoof1.addBox(0.0F, -4.0F, -5.0F, 4, 1, 6);
        this.CabinRoof1.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.CabinRoof1.setTextureSize(128, 64);
        this.CabinRoof1.mirror = true;
        setRotation(this.CabinRoof1, 0.0174533F, 0.0174533F, 0.0F);
        
        this.CabinRoof2 = new ModelRenderer(this, 92, 15);
        this.CabinRoof2.addBox(1.0F, -5.0F, -5.0F, 2, 1, 6);
        this.CabinRoof2.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.CabinRoof2.setTextureSize(128, 64);
        this.CabinRoof2.mirror = true;
        setRotation(this.CabinRoof2, 0.0174533F, 0.0174533F, 0.0F);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
       super.render(entity, f, f1, f2, f3, f4, f5);
       setRotationAngles(f, f1, f2, f3, f4, f5, entity);
       
       this.GlobePillar1.render(f5);
       this.GlobePillar2.render(f5);
       this.GlobePillar3.render(f5);
       this.GlobePillar4.render(f5);
       this.GlobeTop1.render(f5);
       this.GlobeTop2.render(f5);
       this.GlobeTop3.render(f5);
       this.GlobeTop4.render(f5);
       this.GlobeFace1.render(f5);
       this.GlobeFace2.render(f5);
       this.GlobeFace3.render(f5);
       this.GlobeFace4.render(f5);
       this.GlobeFace5.render(f5);
       this.GlobeBottom.render(f5);
       this.BaseTop.render(f5);
       this.TreeTrunk.render(f5);
       this.TreeLeaves1.render(f5);
       this.TreeLeaves2.render(f5);
       this.CabinMain.render(f5);
       this.CabinRoof1.render(f5);
       this.CabinRoof2.render(f5);
    }
               
    public void render(float f5)
    {
        this.GlobePillar1.render(f5);
        this.GlobePillar2.render(f5);
        this.GlobePillar3.render(f5);
        this.GlobePillar4.render(f5);
        this.GlobeTop1.render(f5);
        this.GlobeTop2.render(f5);
        this.GlobeTop3.render(f5);
        this.GlobeTop4.render(f5);
        this.GlobeFace1.render(f5);
        this.GlobeFace2.render(f5);
        this.GlobeFace3.render(f5);
        this.GlobeFace4.render(f5);
        this.GlobeFace5.render(f5);
        this.GlobeBottom.render(f5);
        this.BaseTop.render(f5);
        this.TreeTrunk.render(f5);
        this.TreeLeaves1.render(f5);
        this.TreeLeaves2.render(f5);
        this.CabinMain.render(f5);
        this.CabinRoof1.render(f5);
        this.CabinRoof2.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}