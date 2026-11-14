package fr.welloznetwork.wellozmod.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelRabbit;
import fr.welloznetwork.wellozmod.entity.passive.EntityRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderRabbit extends RenderLiving
{   
    private static final ResourceLocation BROWN = new ResourceLocation("wellozmod:textures/entity/rabbit/brown.png");
    private static final ResourceLocation WHITE = new ResourceLocation("wellozmod:textures/entity/rabbit/white.png");
    private static final ResourceLocation BLACK = new ResourceLocation("wellozmod:textures/entity/rabbit/black.png");
    private static final ResourceLocation GOLD = new ResourceLocation("wellozmod:textures/entity/rabbit/gold.png");
    private static final ResourceLocation SALT = new ResourceLocation("wellozmod:textures/entity/rabbit/salt.png");
    private static final ResourceLocation WHITE_SPLOTCHED = new ResourceLocation("wellozmod:textures/entity/rabbit/white_splotched.png");
    private static final ResourceLocation TOAST = new ResourceLocation("wellozmod:textures/entity/rabbit/toast.png");
    private static final ResourceLocation CAERBANNOG = new ResourceLocation("wellozmod:textures/entity/rabbit/caerbannog.png");
    
    public RenderRabbit()
    {
        super(new ModelRabbit(), 0.5F);
    }
    
    protected void preRenderCallback(EntityLivingBase entityLivingBase, float partialTicks)
    {
        GL11.glScalef(0.65F, 0.65F, 0.65F);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        EntityRabbit entityRabbit = (EntityRabbit) entity;
        
        String s = EnumChatFormatting.getTextWithoutFormattingCodes(entityRabbit.getCommandSenderName());
        
        if (s != null && s.equals("Toast"))
        {
            return TOAST;
        }
        
        else
        {
             switch (entityRabbit.getRabbitType())
             {
                 case 0:
                 default:
                     return BROWN;
                     
                 case 1:
                     return WHITE;
                     
                 case 2:
                     return BLACK;
                     
                 case 3:
                     return WHITE_SPLOTCHED;
                     
                 case 4:
                     return GOLD;
                     
                 case 5:
                     return SALT;
             }
        }
    }
}