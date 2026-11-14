package fr.welloznetwork.wellozmod.client.renderer.entity.monster;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelAquaCreeper;
import fr.welloznetwork.wellozmod.entity.monster.EntityAquaCreeper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderAquaCreeper extends RenderLiving
{
    private final ModelBase modelBase = (ModelBase) new ModelAquaCreeper();
    
    public RenderAquaCreeper()
    {
        super(new ModelAquaCreeper(), 0.5F);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("wellozmod:textures/entity/creeper/aqua_creeper.png");
    }
    
    protected void preRenderCallback(EntityLivingBase entityLivingBase, float partialTicks)
    {
        EntityAquaCreeper entityAquaCreeper = (EntityAquaCreeper) entityLivingBase;
        int size = entityAquaCreeper.getInflateSize();
        
        GL11.glScalef((float) (size * 0.009D + 1.0D), (float) (size * 0.009D + 1.0D), (float) (-size * 0.0025D + 1.0D));
    }
    
    protected int shouldRenderPass(EntityLivingBase entityLivingBase, int pass, float partialTicks)
    {
        EntityAquaCreeper entityAquaCreeper = (EntityAquaCreeper) entityLivingBase;
        
        if (entityAquaCreeper.getInflateSize() > 0)
        {
            if (entityAquaCreeper.isInvisible())
            {
                GL11.glDepthMask(false);
            }
            
            else
            {
                GL11.glDepthMask(true);
            }
            
            if (pass == 1)
            {
                float timer = entityAquaCreeper.ticksExisted + partialTicks;
                this.bindTexture(new ResourceLocation("wellozmod:textures/entity/creeper/aqua_creeper_overlay.png"));
                
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                
                float y = timer * 0.05F;
                
                GL11.glTranslatef(0.0F, y, 0.0F);
                
                this.setRenderPassModel(this.modelBase);
                
                GL11.glMatrixMode(5888);
                GL11.glEnable(3042);
                GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
                GL11.glDisable(2896);
                GL11.glBlendFunc(1, 1);
                
                return 1;
            }
            
            if (pass == 2)
            {
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
            }
        }
        
        return -1;
    }
    
    protected int inheritRenderPass(EntityLivingBase entityLivingBase, int pass, float partialTicks)
    {
        return -1;
    }
}