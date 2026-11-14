package fr.welloznetwork.wellozmod.client.cosmetic.emote;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.TweenAccessor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class ModelAccessor implements TweenAccessor<ModelBiped>
{
    public static final ModelAccessor INSTANCE = new ModelAccessor();
    
    public int getValues(ModelBiped modelBiped, int tweenType, float[] returnValues)
    {
        ModelRenderer modelRenderer = this.getBodyPart(modelBiped, tweenType / 3 * 3);
        
        if (modelRenderer == null)
        {
            return 0;
        }
        
        if (tweenType % 3 == 0)
        {
            returnValues[0] = modelRenderer.rotateAngleX;
        }
        
        else if (tweenType % 3 == 1)
        {
            returnValues[0] = modelRenderer.rotateAngleY;
        }
        
        else
        {
            returnValues[0] = modelRenderer.rotateAngleZ;
        }
        
        return 1;
    }
    
    private ModelRenderer getBodyPart(ModelBiped modelBiped, int part)
    {
        switch (part)
        {
            case 0:
                return modelBiped.bipedHead;
                
            case 3:
                return modelBiped.bipedBody;
                
            case 6:
                return modelBiped.bipedRightArm;
                
            case 9:
                return modelBiped.bipedLeftArm;
                
            case 15:
                return modelBiped.bipedRightLeg;
                
            case 18:
                return modelBiped.bipedLeftLeg;
        }
        
        return null;
    }
    
    public void setValues(ModelBiped modelBiped, int tweenType, float[] newValues)
    {
        ModelRenderer modelRenderer = this.getBodyPart(modelBiped, tweenType / 3 * 3);
        
        this.messWithModel(modelBiped, modelRenderer, tweenType % 3, newValues[0]);
    }
    
    private void messWithModel(ModelBiped modelBiped, ModelRenderer modelRenderer, int axis, float value)
    {
        if (modelRenderer == null)
        {
            return;
        }
        
        if (axis == 0)
        {
            modelRenderer.rotateAngleX = value;
        }
        
        else if (axis == 1)
        {
            modelRenderer.rotateAngleY = value;
        }
        
        else if (axis == 2)
        {
            modelRenderer.rotateAngleZ = value;
        }
        
        if (modelRenderer == modelBiped.bipedHead)
        {
            this.messWithModel(modelBiped, modelBiped.bipedHeadwear, axis, value);
        }
    }
}