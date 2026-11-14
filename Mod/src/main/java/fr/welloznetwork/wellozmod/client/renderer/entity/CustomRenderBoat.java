package fr.welloznetwork.wellozmod.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.client.model.CustomModelBoat;
import fr.welloznetwork.wellozmod.entity.item.CustomEntityBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@Custom(RenderBoat.class)
@SideOnly(Side.CLIENT)
public class CustomRenderBoat extends Render
{
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[] {new ResourceLocation("wellozmod:textures/entity/boats/oak.png"), new ResourceLocation("wellozmod:textures/entity/boats/spruce.png"), new ResourceLocation("wellozmod:textures/entity/boats/birch.png"), new ResourceLocation("wellozmod:textures/entity/boats/jungle.png"), new ResourceLocation("wellozmod:textures/entity/boats/acacia.png"), new ResourceLocation("wellozmod:textures/entity/boats/dark_oak.png"), new ResourceLocation("wellozmod:textures/entity/boats/cherry.png"), new ResourceLocation("wellozmod:textures/entity/boats/mangrove.png")};
    private CustomModelBoat customModelBoat = new CustomModelBoat();
    
    public CustomRenderBoat()
    {
        this.shadowSize = 0.5F;
    }
    
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks)
    {
        if (!(entity instanceof CustomEntityBoat))
        {
            return;
        }
        
        CustomEntityBoat customEntityBoat = (CustomEntityBoat) entity;
        
        GL11.glPushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(customEntityBoat, yaw, partialTicks);
        this.bindEntityTexture(customEntityBoat);
        this.customModelBoat.render(customEntityBoat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.renderExtraBoatContents(customEntityBoat, partialTicks);
        GL11.glPopMatrix();
        this.renderMultipass(customEntityBoat, x, y, z, yaw, partialTicks);
    }
    
    protected void renderExtraBoatContents(CustomEntityBoat customEntityBoat, float partialTicks) {}
    
    public void setupRotation(CustomEntityBoat customEntityBoat, float yaw, float pitch)
    {
        GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
        float f = (float) customEntityBoat.getTimeSinceHit() - pitch;
        float f1 = customEntityBoat.getDamageTaken() - pitch;
        
        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }
        
        if (f > 0.0F)
        {
            GL11.glRotatef(MathHelper.sin(f) * f * f1 / 10.0F * (float) customEntityBoat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }
        
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TEXTURE[entity instanceof CustomEntityBoat ? ((CustomEntityBoat) entity).getBoatType().ordinal() : 0];
    }
    
    public void setupTranslation(double x, double y, double z)
    {
        GL11.glTranslatef((float) x, (float) y + 0.375F, (float) z);
    }
    
    public void renderMultipass(CustomEntityBoat customEntityBoat, double x, double y, double z, float yaw, float partialTicks)
    {
        GL11.glPushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(customEntityBoat, yaw, partialTicks);
        this.bindEntityTexture(customEntityBoat);
        this.customModelBoat.renderMultipass(customEntityBoat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.renderExtraBoatContents(customEntityBoat, partialTicks);
        GL11.glPopMatrix();
    }
}