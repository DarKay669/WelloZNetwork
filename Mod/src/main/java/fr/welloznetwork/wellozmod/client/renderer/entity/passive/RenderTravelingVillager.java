package fr.welloznetwork.wellozmod.client.renderer.entity.passive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderTravelingVillager extends RenderLiving
{
    public RenderTravelingVillager()
    {
        super(new ModelVillager(0.0F), 0.5F);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("wellozmod:textures/entity/traveling_villager.png");
    }
}