package fr.welloznetwork.wellozmod.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.boss.EntityWhiter;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderWhiter extends RenderLiving
{
    private static final ResourceLocation INVULNERABLE_WHITER_TEXTURES = new ResourceLocation("textures/entity/whiter/whiter_invulnerable.png");
    private static final ResourceLocation WHITER_TEXTURES = new ResourceLocation("wellozmod:textures/entity/whiter/whiter.png");
    private int layer;
    
    public RenderWhiter()
    {
        super(new ModelWither(), 1.0F);
        this.layer = ((ModelWither) this.mainModel).func_82903_a();
    }
    
    public void doRender(EntityWhiter entityWhiteWither, double x, double y, double z, float entityYaw, float partialTicks)
    {
        BossStatus.setBossStatus(entityWhiteWither, true);
        int i = ((ModelWither) this.mainModel).func_82903_a();
        
        if (i != this.layer)
        {
            this.layer = i;
            this.mainModel = new ModelWither();
        }
        
        super.doRender(entityWhiteWither, x, y, z, entityYaw, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(EntityWhiter entityWhiteWither)
    {
        int i = entityWhiteWither.func_82212_n();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? INVULNERABLE_WHITER_TEXTURES : WHITER_TEXTURES;
    }
    
    protected void preRenderCallback(EntityWhiter entityWhiteWither, float partialTicks)
    {
        float f = 2.0F;
        int i = entityWhiteWither.func_82212_n();
        
        if (i > 0)
        {
            f -= ((float) i - partialTicks) / 220.0F * 0.5F;
        }
        
        GL11.glScalef(f, f, f);
    }
    
    protected int shouldRenderPass(EntityWhiter entityWhiteWither, int pass, float partialTicks)
    {
        if (entityWhiteWither.isArmored())
        {
            if (entityWhiteWither.isInvisible())
            {
                GL11.glDepthMask(false);
            }
            
            else
            {
                GL11.glDepthMask(true);
            }
            
            if (pass == 1)
            {
                this.bindTexture(INVULNERABLE_WHITER_TEXTURES);
                
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glTranslatef(MathHelper.cos((float) entityWhiteWither.ticksExisted + partialTicks * 0.02F) * 3.0F, (float) entityWhiteWither.ticksExisted + partialTicks * 0.01F, 0.0F);
                
                this.setRenderPassModel(this.mainModel);
                
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);                
                GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                GL11.glTranslatef(0.0F, -0.01F, 0.0F);
                GL11.glScalef(1.1F, 1.1F, 1.1F);
                
                return 1;
            }
            
            if (pass == 2)
            {
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
        
        return -1;
    }
    
    protected int inheritRenderPass(EntityWhiter entityWhiteWither, int pass, float partialTicks)
    {
        return -1;
    }
    
    public void doRender(EntityLiving entityLiving, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityWhiter) entityLiving, x, y, z, entityYaw, partialTicks);
    }
    
    protected void preRenderCallback(EntityLivingBase entityLivingBase, float partialTicks)
    {
        this.preRenderCallback((EntityWhiter) entityLivingBase, partialTicks);
    }
    
    protected int shouldRenderPass(EntityLivingBase entityLivingBase, int pass, float partialTicks)
    {
        return this.shouldRenderPass((EntityWhiter) entityLivingBase, pass, partialTicks);
    }
    
    protected int inheritRenderPass(EntityLivingBase entityLivingBase, int pass, float partialTicks)
    {
        return this.inheritRenderPass((EntityWhiter) entityLivingBase, pass, partialTicks);
    }
    
    public void doRender(EntityLivingBase entityLivingBase, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityWhiter) entityLivingBase, x, y, z, entityYaw, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityWhiter) entity);
    }
    
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityWhiter) entity, x, y, z, entityYaw, partialTicks);
    }
}