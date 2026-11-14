package fr.welloznetwork.wellozmod.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelTechnobladeCrown;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderTechnobladeCrown extends RenderPig
{
    private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png"), TEXTURE = new ResourceLocation("wellozmod:textures/entity/pig/technoblade_crown.png");
    private final ModelBase modelBase = new ModelTechnobladeCrown();
    private ModelBase prevRenderPassModel;
    
    public RenderTechnobladeCrown()
    {
        super(new ModelPig(), new ModelPig(0.5F), 0.7F);
    }
    
    protected int shouldRenderPass(EntityPig entityPig, int p_77032_2_, float p_77032_3_)
    {
        if ("Technoblade".equals(entityPig.getCustomNameTag()))
        {
            if (p_77032_2_ == 1)
            {
                this.prevRenderPassModel = this.renderPassModel;
                this.renderPassModel = this.modelBase;
                this.modelBase.isChild = this.mainModel.isChild;
                this.bindTexture(TEXTURE);
                return 1;
            }
            
            if (p_77032_2_ == 2)
            {
                this.renderPassModel = this.prevRenderPassModel;
            }
        }
        
        return super.shouldRenderPass(entityPig, p_77032_2_, p_77032_3_);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return pigTextures;
    }
}