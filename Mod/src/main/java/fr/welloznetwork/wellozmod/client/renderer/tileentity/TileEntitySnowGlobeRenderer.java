package fr.welloznetwork.wellozmod.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelSnowGlobe;
import fr.welloznetwork.wellozmod.tileentity.TileEntitySnowGlobe;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TileEntitySnowGlobeRenderer extends TileEntitySpecialRenderer
{
    static final float scale = 0.0625F;
    private ModelSnowGlobe model = new ModelSnowGlobe();
    
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
    {
        renderAModelAt((TileEntitySnowGlobe) tileEntity, x, y, z, partialTicks);
    }
    
    public void renderAModelAt(TileEntitySnowGlobe tileEntity, double x, double y, double z, float partialTicks)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(((float) x + 0.5F), ((float) y + 0.75F), ((float) z + 0.5F));
        bindTexture(new ResourceLocation("wellozmod:textures/models/snow_globe.png"));
        TileEntitySnowGlobe tileEntitySnowGlobe = tileEntity;
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        this.model.render(0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}