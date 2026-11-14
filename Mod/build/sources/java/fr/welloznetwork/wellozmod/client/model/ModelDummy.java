package fr.welloznetwork.wellozmod.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;

@SideOnly(Side.CLIENT)
public class ModelDummy extends ModelBiped
{
    private float scale = 0.0625F;
    
    public void renderDummey()
    {
        this.bipedHead.render(this.scale);
        this.bipedBody.render(this.scale);
        this.bipedRightArm.render(this.scale);
        this.bipedLeftArm.render(this.scale);
        this.bipedRightLeg.render(this.scale);
        this.bipedLeftLeg.render(this.scale);
        this.bipedHeadwear.render(this.scale);
    }
    
    public void renderHead()
    {
        this.bipedHead.render(this.scale);
        this.bipedHeadwear.render(this.scale);
    }
    
    public void renderChest()
    {
        this.bipedBody.render(this.scale);
        this.bipedRightArm.render(this.scale);
        this.bipedLeftArm.render(this.scale);
    }
    
    public void renderLegs()
    {
        this.bipedRightLeg.render(this.scale);
        this.bipedLeftLeg.render(this.scale);
    }
    
    public void renderFeet()
    {
        this.bipedRightLeg.render(this.scale);
        this.bipedLeftLeg.render(this.scale);
    }
}