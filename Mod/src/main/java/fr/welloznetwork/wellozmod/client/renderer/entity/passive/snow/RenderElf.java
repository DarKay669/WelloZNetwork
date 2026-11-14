package fr.welloznetwork.wellozmod.client.renderer.entity.passive.snow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelElf;
import fr.welloznetwork.wellozmod.entity.passive.EntityElf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderElf extends RenderLiving
{
    protected ModelElf model;
    
    public RenderElf()
    {
        super(new ModelElf(), 0.3F);
        this.model = (ModelElf) this.mainModel;
    }
    
    public void renderGoblin(EntityElf entityElf, double par2, double par4, double par6, float par8, float par9)
    {
        doRender((EntityLiving) entityElf, par2, par4, par6, par8, par9);
    }
    
    public void doRenderLiving(EntityLiving entityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        renderGoblin((EntityElf) entityLiving, par2, par4, par6, par8, par9);
    }
    
    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
    {
        renderGoblin((EntityElf) entity, d0, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("wellozmod:textures/entity/elf.png");
    }
}