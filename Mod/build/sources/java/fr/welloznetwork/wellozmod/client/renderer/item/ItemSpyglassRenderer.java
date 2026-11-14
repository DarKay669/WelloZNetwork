package fr.welloznetwork.wellozmod.client.renderer.item;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelSpyglass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class ItemSpyglassRenderer implements IItemRenderer
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("wellozmod:textures/models/spyglass.png");
    private ModelSpyglass modelSpyglass = new ModelSpyglass();
    private static Minecraft mc = null;
    private static final RenderItem RENDER_ITEM = new RenderItem();
    
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType)
    {
        return (itemRenderType.equals(ItemRenderType.EQUIPPED) || itemRenderType.equals(ItemRenderType.EQUIPPED_FIRST_PERSON) || itemRenderType.equals(ItemRenderType.INVENTORY) || itemRenderType.equals(ItemRenderType.ENTITY));
    }
    
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper)
    {
        return (itemRendererHelper.equals(ItemRendererHelper.ENTITY_BOBBING) || itemRendererHelper.equals(ItemRendererHelper.ENTITY_ROTATION));
    }
    
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data)
    {
        float offsetX, offsetY, offsetZ, angle;
        IIcon iicon;
        int color;
        EntityLivingBase entityLivingBase = null;
        
        if (data.length > 1)
        {
            if (data[1] instanceof EntityLivingBase)
            {
                entityLivingBase = (EntityLivingBase) data[1];
            }
        }
        
        if (this.mc == null)
        {
            this.mc = Minecraft.getMinecraft();
        }
        
        switch (itemRenderType)
        {
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
            case ENTITY:
                offsetX = -0.9F;
                offsetY = -1.1F;
                offsetZ = 0.0F;
                angle = 210.0F;
                
                if (itemRenderType == ItemRenderType.EQUIPPED || itemRenderType == ItemRenderType.EQUIPPED_FIRST_PERSON)
                {
                    boolean flag = false;
                    
                    if (entityLivingBase instanceof EntityPlayer)
                    {
                        EntityPlayer entityPlayer = (EntityPlayer) entityLivingBase;
                        
                        if (entityPlayer.getItemInUse() != null)
                        {
                            flag = true;
                        }
                        
                        if (flag && itemRenderType == ItemRenderType.EQUIPPED_FIRST_PERSON)
                        {
                            return;
                        }
                    }
                    
                    if (flag)
                    {
                        angle -= 90.0F;
                        offsetY = (float) (offsetY - 0.5D);
                        
                        if (itemRenderType == ItemRenderType.EQUIPPED) 
                        {
                            offsetX = (float) (offsetX + 1.05D);
                            offsetZ = (float) (offsetZ + 0.12D);
                        }
                        
                        else
                        {
                            offsetX = (float) (offsetX + 0.8D);
                            offsetZ = (float) (offsetZ - 0.4D);
                        }
                    }
                }
                
                GL11.glPushMatrix();
                this.mc.renderEngine.bindTexture(TEXTURE);
                GL11.glRotatef(angle, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(offsetX, offsetY, offsetZ);
                this.modelSpyglass.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                GL11.glTranslatef(offsetX * -1.0F, offsetY * -1.0F, offsetZ * -1.0F);
                GL11.glRotatef(angle * -1.0F, 0.0F, 0.0F, 1.0F);
                GL11.glPopMatrix();
                break;
                
            case INVENTORY:
                iicon = itemStack.getItem().getIconFromDamage(0);
                color = itemStack.getItem().getColorFromItemStack(itemStack, 0);
                
                GL11.glDisable(2896);
                GL11.glEnable(3008);
                RENDER_ITEM.renderIcon(0, 0, iicon, 16, 16);
                GL11.glDisable(3008);
                GL11.glEnable(2896);
                break;
                
            default:
                break;
        }
    }
    
    private void glSetColor(int color, float alpha)
    {
        GL11.glColor4f(Color.red.getRGB() / 255.0F, Color.green.getRGB() / 255.0F, Color.blue.getRGB() / 255.0F, alpha);
    }
}