package fr.welloznetwork.wellozmod.client.renderer.entity.monster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.monster.EntityHerobrine;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderHerobrine extends RenderBiped
{
	public static final ResourceLocation TEXTURE = new ResourceLocation("wellozmod:textures/entity/herobrine.png");

	public RenderHerobrine()
	{
		super(new ModelBiped(), 0.5F);
	}
	
	public ResourceLocation bindEntityTexture(EntityHerobrine entityHerobrine)
	{
		return TEXTURE;
	}
	
	protected ResourceLocation getEntityTexture(EntityLiving entityLiving)
	{
		return bindEntityTexture((EntityHerobrine) entityLiving);
	}
}