package fr.welloznetwork.wellozmod.client.model;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelPenguin extends ModelBase
{
	private ModelRenderer body, rightarm, leftarm, rightleg, leftleg, head, beak;
	
	public ModelPenguin()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		
		this.body = new ModelRenderer(this, 32, 0);
		this.body.addBox(-4.0F, 0.0F, -4.0F, 8, 9, 8);
		this.body.setRotationPoint(0.0F, 14.0F, 0.0F);
		
		this.rightarm = new ModelRenderer(this, 34, 18);
		this.rightarm.addBox(-1.0F, -1.0F, -2.0F, 1, 8, 4);
		this.rightarm.setRotationPoint(-4.0F, 15.0F, 0.0F);
		
		this.leftarm = new ModelRenderer(this, 24, 18);
		this.leftarm.addBox(0.0F, -1.0F, -2.0F, 1, 8, 4);
		this.leftarm.setRotationPoint(4.0F, 15.0F, 0.0F);
		
		this.leftarm.mirror = true;
		
		this.rightleg = new ModelRenderer(this, 0, 16);
		this.rightleg.addBox(-2.0F, 0.0F, -5.0F, 4, 1, 8);
		this.rightleg.setRotationPoint(-2.0F, 23.0F, 0.0F);
		this.rightleg.setTextureSize(64, 32);
		
		this.leftleg = new ModelRenderer(this, 0, 16);
		this.leftleg.addBox(-2.0F, 0.0F, -5.0F, 4, 1, 8);
		this.leftleg.setRotationPoint(2.0F, 23.0F, 0.0F);
		
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-3.5F, -4.0F, -3.5F, 7, 5, 7);
		this.head.setRotationPoint(0.0F, 13.0F, 0.0F);
		
		this.beak = new ModelRenderer(this, 0, 13);
		this.beak.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2);
		this.beak.setRotationPoint(0.0F, -1.0F, -4.0F);
		
		this.head.addChild(this.beak);
	}
	
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7);
		
		if (this.isChild)
		{
			float f = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 5.0F * par7, 0.75F * par7);
			this.head.render(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f, 1.0F / f, 1.0F / f);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			this.body.render(par7);
			this.rightleg.render(par7);
			this.leftleg.render(par7);
			this.rightarm.render(par7);
			this.leftarm.render(par7);
			GL11.glPopMatrix();
		} else {
			this.head.render(par7);
			this.body.render(par7);
			this.rightleg.render(par7);
			this.leftleg.render(par7);
			this.rightarm.render(par7);
			this.leftarm.render(par7);
		}
	}
	
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		this.head.rotateAngleX = par5 / 57.295776F;
		this.head.rotateAngleY = par4 / 57.295776F;
		
		this.rightleg.rotateAngleX = MathHelper.cos(par1) * 0.7F * par2;
		this.leftleg.rotateAngleX = MathHelper.cos(par1 + 3.1415927F) * 0.7F * par2;
		
		this.rightarm.rotateAngleZ = par3;
		this.leftarm.rotateAngleZ = -par3;
	}
}