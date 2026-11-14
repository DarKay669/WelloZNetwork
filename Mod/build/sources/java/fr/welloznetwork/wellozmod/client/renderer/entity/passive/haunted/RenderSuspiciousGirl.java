package fr.welloznetwork.wellozmod.client.renderer.entity.passive.haunted;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.dev.DeveloperHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSuspiciousGirl extends RenderLiving
{
    public RenderSuspiciousGirl()
    {
        super(new ModelBiped(0.0F), 0.5F);
    }
    
    @DeveloperHelper("suspicious_girl.png when the texture Is ready")
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("wellozmod:textures/entity/haunted/ghost.png");
    }
}