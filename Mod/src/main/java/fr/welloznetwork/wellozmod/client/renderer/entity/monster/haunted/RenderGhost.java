package fr.welloznetwork.wellozmod.client.renderer.entity.monster.haunted;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderGhost extends RenderLiving
{    
    public RenderGhost()
    {
        super(new ModelBiped(), 0.0F);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("wellozmod:textures/entity/haunted/ghost.png");
    }
}