package fr.welloznetwork.wellozmod.modules.jobs.client.ui;

import java.awt.Color;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.modules.jobs.eep.JobsEEP;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobData;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobType;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UISubJobs extends GuiScreen
{
    private GuiScreen parentScreen;
    private JobType jobType;
    private int guiWidth = 256, guiHeight = 160;
    
    public UISubJobs(GuiScreen parentScreen, JobType jobType)
    {
        this.parentScreen = parentScreen;
        this.jobType = jobType;
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        
        int x = (this.width - this.guiWidth) / 2;
        int y = (this.height - this.guiHeight) / 2;
        
        this.mc.getTextureManager().bindTexture(new ResourceLocation("wellozmod:textures/gui/containers/jobs.png"));
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        this.drawTexturedModalRect(x, y, 0, 0, this.guiWidth, this.guiHeight);
        
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2.0F, y + 10, 0.0F);
        GL11.glScalef(1.5F, 1.5F, 1.0F);
        
        this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs." + this.jobType.name().toLowerCase()), -this.fontRendererObj.getStringWidth(I18n.format("gui.jobs." + this.jobType.name().toLowerCase())) / 2, 0, Color.WHITE.getRGB());
        
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2.0F, y + 35, 0.0F);
        
        String xpText = I18n.format("gui.jobs.xp");
        
        int craftWidth = -this.fontRendererObj.getStringWidth("Craft") / 2;
        int xpWidth = -this.fontRendererObj.getStringWidth(xpText) / 2;
        
        this.fontRendererObj.drawStringWithShadow("Craft", craftWidth, 40, Color.WHITE.getRGB());
        
        if (mouseX - this.width / 2 >= craftWidth && mouseX - this.width / 2 <= craftWidth + this.fontRendererObj.getStringWidth("Craft") && mouseY - (y + 35) >= 40 && mouseY - (y + 35) <= 40 + this.fontRendererObj.FONT_HEIGHT)
        {
            this.fontRendererObj.drawStringWithShadow("Craft", craftWidth, 40, 16761095);
        }
        
        this.fontRendererObj.drawStringWithShadow(xpText, xpWidth, 65, Color.WHITE.getRGB());
        
        if (mouseX - this.width / 2 >= xpWidth && mouseX - this.width / 2 <= xpWidth + this.fontRendererObj.getStringWidth(xpText) && mouseY - (y + 35) >= 65 && mouseY - (y + 35) <= 65 + this.fontRendererObj.FONT_HEIGHT)
        {
            this.fontRendererObj.drawStringWithShadow(xpText, xpWidth, 65, 16761095);
        }
        
        if (mouseX - this.width / 2 >= xpWidth && mouseX - this.width / 2 <= this.fontRendererObj.getStringWidth(xpText) / 2 && mouseY - (y + 35) >= 65 && mouseY - (y + 35) <= 65 + this.fontRendererObj.FONT_HEIGHT)
        {
            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
            
            switch (this.jobType)
            {
                case HUNTER:
                    this.drawHoveringText(Arrays.asList(I18n.format("gui.jobs.xpHunter")), mouseX - this.width / 2, mouseY - (y + 35), this.fontRendererObj);
                    break;
                    
                case MINER:
                    this.drawHoveringText(Arrays.asList(I18n.format("gui.jobs.xpMiner")), mouseX - this.width / 2, mouseY - (y + 35), this.fontRendererObj);
                    break;
                    
                case LUMBERJACK:
                    this.drawHoveringText(Arrays.asList(I18n.format("gui.jobs.xpLumberjack")), mouseX - this.width / 2, mouseY - (y + 35), this.fontRendererObj);
                    break;
            }
            
            GL11.glPopAttrib();
        }
        
        GL11.glPopMatrix();
        
        this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs.back"), x + 5, y + 5, Color.WHITE.getRGB());
        
        if (mouseX >= x + 5 && mouseY >= y + 5 && mouseX < x + 5 + this.fontRendererObj.getStringWidth(I18n.format("gui.jobs.back")) && mouseY < y + 15)
        {
            this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs.back"), x + 5, y + 5, 16761095);
        }
        
        JobsEEP jobsEEP = JobsEEP.get(this.mc.thePlayer);
        
        if (jobsEEP != null)
        {
            int totalLevel = 0, maxLevel = 0, totalXp = 0, maxXp = 0;
            
            JobData jobData = jobsEEP.getJob(this.jobType);
            
            if (jobData != null)
            {
                totalLevel += jobData.getLevel();
                maxLevel += 20;
                totalXp += jobData.getXp();
                maxXp += jobData.getMaxXp();
            }
            
            int barWidth = 235, barX = (this.width - barWidth) / 2, barY = y + 28;
            
            drawRect(barX, barY, barX + barWidth, barY + 18, Color.BLACK.getRGB());
            
            if (jobData.getLevel() >= 20)
            {
                drawRect(barX + 1, barY + 1, barX + (int) ((double) jobData.getXpInCurrentLevel() / jobData.getXpForNextLevel() * barWidth), barY + 17, new Color(165, 16, 146).getRGB());
            }
            
            else
            {
                drawRect(barX + 1, barY + 1, barX + (int) ((double) jobData.getXpInCurrentLevel() / jobData.getXpForNextLevel() * barWidth), barY + 17, new Color(4, 172, 64).getRGB());
            }
            
            this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs.level") + " " + totalLevel, barX + (barWidth - this.fontRendererObj.getStringWidth(I18n.format("gui.jobs.level") + " " + totalLevel)) / 2, barY + 5, Color.WHITE.getRGB());
            
            if (mouseX >= barX && mouseX <= barX + barWidth && mouseY >= barY && mouseY <= barY + 14)
            {
                this.drawHoveringText(Arrays.asList(jobData.getXpInCurrentLevel() + "/" + jobData.getXpForNextLevel() + " XP"), mouseX, mouseY, this.fontRendererObj);
            }
        }
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        int x = (this.width - this.guiWidth) / 2;
        int y = (this.height - this.guiHeight) / 2;
        int craftWidth = -this.fontRendererObj.getStringWidth("Craft") / 2;
        
        if (mouseX >= x + 5 && mouseY >= y + 5 && mouseX < x + 5 + this.fontRendererObj.getStringWidth(I18n.format("gui.jobs.back")) && mouseY < y + 15)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        if (mouseX - this.width / 2 >= craftWidth && mouseX - this.width / 2 <= craftWidth + this.fontRendererObj.getStringWidth("Craft") && mouseY - (y + 35) >= 40 && mouseY - (y + 35) <= 40 + this.fontRendererObj.FONT_HEIGHT)
        {
            this.mc.displayGuiScreen(new UICraftList(this, this.jobType));
        }
        
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}