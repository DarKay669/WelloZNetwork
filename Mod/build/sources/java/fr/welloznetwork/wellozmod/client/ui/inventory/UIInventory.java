package fr.welloznetwork.wellozmod.client.ui.inventory;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.client.renderer.CustomInventoryEffectRenderer;
import fr.welloznetwork.wellozmod.modules.jobs.client.ui.UIJobs;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class UIInventory extends CustomInventoryEffectRenderer
{
    private float oldMouseX, oldMouseY;
    private boolean isActivePotionEffects = false;
    
    public UIInventory(EntityPlayer entityPlayer)
    {
        super(entityPlayer.inventoryContainer);
        this.allowUserInput = true;
    }
    
    public void updateScreen()
    {
        super.updateScreen();
        
        boolean state = !this.mc.thePlayer.getActivePotionEffects().isEmpty();
        
        if (state != this.isActivePotionEffects)
        {
            this.isActivePotionEffects = state;
            this.initGui();
        }
        
        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
    }
    
    public void initGui()
    {
        super.initGui();
        
        this.buttonList.clear();
        
        if (Constants.V2)
        {
            this.buttonList.add(new GuiButton(0, ((this.width - 8) / 2 + 23) + (this.guiLeft - (this.width - this.xSize) / 2), (this.height - 101) / 2 + 29, 63, 20, I18n.format("gui.cosmetics.inventory")));
        }
        
        this.buttonList.add(new GuiButton(0, ((this.width - 8) / 2 + 23) + (this.guiLeft - (this.width - this.xSize) / 2), (this.height - 101) / 2 + 29, 63, 20, I18n.format("gui.jobs")));
        
        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
        
        else
        {
            super.initGui();
        }
    }
    
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(I18n.format("container.crafting"), 87, 16, 4210752);
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }
    
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_147001_a);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        this.drawEntityOnScreen(i + 51, j + 75, 30, (float) (i + 51) - this.oldMouseX, (float) (j + 75 - 50) - this.oldMouseY, this.mc.thePlayer);
    }
    
    private void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase entityLivingBase)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) posX, (float) posY, 50.0F);
        GL11.glScalef((float) (-scale), (float) scale, (float) scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entityLivingBase.renderYawOffset;
        float f3 = entityLivingBase.rotationYaw;
        float f4 = entityLivingBase.rotationPitch;
        float f5 = entityLivingBase.prevRotationYawHead;
        float f6 = entityLivingBase.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entityLivingBase.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
        entityLivingBase.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
        entityLivingBase.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
        GL11.glTranslatef(0.0F, entityLivingBase.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        entityLivingBase.renderYawOffset = f2;
        entityLivingBase.rotationYaw = f3;
        entityLivingBase.rotationPitch = f4;
        entityLivingBase.prevRotationYawHead = f5;
        entityLivingBase.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton.id == 0)
        {
            this.mc.displayGuiScreen(new UIJobs(this));
        }
    }
}