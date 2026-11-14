package fr.welloznetwork.wellozmod.client.renderer.entity.layers;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelElytra;
import fr.welloznetwork.wellozmod.item.ItemElytra;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class LayerElytra
{
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("wellozmod:textures/entity/elytra.png"), ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private static final ModelElytra MODEL_ELYTRA = new ModelElytra();
    
    public static void doRenderLayer(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        ItemStack itemStack = ItemElytra.getElytra(entityLivingBase);
        
        if (entityLivingBase instanceof AbstractClientPlayer && itemStack != null)
        {
            AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) entityLivingBase;
            
            GL11.glPushAttrib(-1);
            
            Minecraft.getMinecraft().renderEngine.bindTexture(abstractClientPlayer.func_152122_n() ? abstractClientPlayer.getLocationCape() : TEXTURE_ELYTRA);
            
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            
            MODEL_ELYTRA.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityLivingBase);
            MODEL_ELYTRA.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            
            if (itemStack.isItemEnchanted())
            {
                renderEnchantedGlint(entityLivingBase, MODEL_ELYTRA, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }
    
    private static void renderEnchantedGlint(EntityLivingBase entityLivingBase, ModelElytra modelElytra, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(ENCHANTED_ITEM_GLINT_RES);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthFunc(514);
        GL11.glDepthMask(false);
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
        
        for (int i = 0; i < 2; i++)
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glBlendFunc(768, 1);
            GL11.glColor4f(0.5F * 0.76F, 0.25F * 0.76F, 0.8F * 0.76F, 1.0F);
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            GL11.glScalef(0.33333334F, 0.33333334F, 0.33333334F);
            GL11.glRotatef(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.0F, entityLivingBase.ticksExisted + partialTicks * (0.001F + i * 0.003F) * 20.0F, 0.0F);
            GL11.glMatrixMode(5888);
            
            modelElytra.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw , headPitch, scale);
        }
        
        GL11.glMatrixMode(5890);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glDepthFunc(515);
        GL11.glDisable(GL11.GL_BLEND);
    }
}