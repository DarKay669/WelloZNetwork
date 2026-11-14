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
public class UIJobs extends GuiScreen
{
    private GuiScreen parentScreen;
    private int guiWidth = 256, guiHeight = 160;
    
    public UIJobs(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
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
        GL11.glTranslatef(this.width / 2.0F, y + 10, 0);
        GL11.glScalef(1.5F, 1.5F, 1.0F);
        
        this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs"), -this.fontRendererObj.getStringWidth(I18n.format("gui.jobs")) / 2, 0, Color.WHITE.getRGB());
        
        GL11.glPopMatrix();
        
        if (!(mouseX >= x + 5 && mouseY >= y + 5 && mouseX < x + 5 + this.fontRendererObj.getStringWidth(I18n.format("gui.jobs.back")) && mouseY < y + 15))
        {
            this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs.back"), x + 5, y + 5, Color.WHITE.getRGB());
        }
        
        else
        {
            this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs.back"), x + 5, y + 5, 16761095);
        }
        
        JobsEEP jobsEEP = JobsEEP.get(this.mc.thePlayer);
        
        if (jobsEEP != null)
        {
            int totalLevel = 0, maxLevel = 0, totalXp = 0, maxXp = 0;
            
            for (JobType jobType : JobType.values())
            {
                JobData jobData = jobsEEP.getJob(jobType);
                
                if (jobData != null)
                {
                    totalLevel += jobData.getLevel();
                    maxLevel += 20;
                    totalXp += jobData.getXp();
                    maxXp += jobData.getMaxXp();
                }
            }
            
            for (JobType jobType : JobType.values())
            {
                JobData jobData = jobsEEP.getJob(jobType);
                
                if (jobData == null)
                {
                    continue;
                }
                
                int barWidth = 70, barX = 0, barY = y + 40;
                
                switch (jobType)
                {
                    case HUNTER:
                        barX = (this.width - barWidth) / 2 - 82;
                        break;
                        
                    case MINER:
                        barX = (this.width - barWidth) / 2;
                        break;
                        
                    case LUMBERJACK:
                        barX = (this.width - barWidth) / 2 + 82;
                        break;
                }
                
                drawRect(barX, barY + 48, barX + barWidth, barY + 60, Color.BLACK.getRGB());
                
                if (jobData.getLevel() >= 20)
                {
                    drawRect(barX + 1, barY + 49, barX + (int) ((double) jobData.getXpInCurrentLevel() / jobData.getXpForNextLevel() * barWidth), barY + 59, new Color(165, 16, 146).getRGB());
                }
                
                else
                {
                    drawRect(barX + 1, barY + 49, barX + (int) ((double) jobData.getXpInCurrentLevel() / jobData.getXpForNextLevel() * barWidth), barY + 59, new Color(4, 172, 64).getRGB());
                }
                
                int textWidth = this.fontRendererObj.getStringWidth(I18n.format("gui.jobs." + jobType.name().toLowerCase()));
                
                this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs." + jobType.name().toLowerCase()), barX + (barWidth - this.fontRendererObj.getStringWidth(I18n.format("gui.jobs." + jobType.name().toLowerCase()))) / 2, barY + 36, Color.WHITE.getRGB());
                
                if (mouseX >= barX + (barWidth - textWidth) / 2 && mouseX <= barX + (barWidth - textWidth) / 2 + textWidth && mouseY >= barY + 36 && mouseY <= barY + 36 + this.fontRendererObj.FONT_HEIGHT)
                {
                    this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs." + jobType.name().toLowerCase()), barX + (barWidth - this.fontRendererObj.getStringWidth(I18n.format("gui.jobs." + jobType.name().toLowerCase()))) / 2, barY + 36, 16761095);
                }
                
                this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs.level") + " " + jobData.getLevel(), barX + (barWidth - this.fontRendererObj.getStringWidth(I18n.format("gui.jobs.level") + " " + jobData.getLevel())) / 2, barY + 64, Color.WHITE.getRGB());
                
                if (mouseX >= barX && mouseX <= barX + barWidth && mouseY >= barY + 48 && mouseY <= barY + 60)
                {
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    
                    this.drawHoveringText(Arrays.asList(jobData.getXpInCurrentLevel() + "/" + jobData.getXpForNextLevel() + " XP"), mouseX, mouseY, this.fontRendererObj);
                    
                    GL11.glPopAttrib();
                }
            }
            
            int barWidth = 235, barX = (this.width - barWidth) / 2, barY = y + 28;
            
            drawRect(barX, barY, barX + barWidth, barY + 18, Color.BLACK.getRGB());
            drawRect(barX + 1, barY + 1, barX + (int) ((double) totalLevel / maxLevel * barWidth), barY + 17, new Color(165, 16, 146).getRGB());
            
            this.fontRendererObj.drawStringWithShadow(I18n.format("gui.jobs.level") + " " + totalLevel + "/" + maxLevel, barX + (barWidth - this.fontRendererObj.getStringWidth(I18n.format("gui.jobs.level") + " " + totalLevel + "/" + maxLevel)) / 2, barY + 5, Color.WHITE.getRGB());
            
            if (mouseX >= barX && mouseX <= barX + barWidth && mouseY >= barY && mouseY <= barY + 14)
            {
                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                
                this.drawHoveringText(Arrays.asList(totalXp + "/" + maxXp + " XP"), mouseX, mouseY, this.fontRendererObj);
                
                GL11.glPopAttrib();
            }
        }
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        int x = (this.width - this.guiWidth) / 2;
        int y = (this.height - this.guiHeight) / 2;
        int barWidth = 70, barX = 0, barY = y + 40;
        
        if (mouseX >= x + 5 && mouseY >= y + 5 && mouseX < x + 5 + this.fontRendererObj.getStringWidth(I18n.format("gui.jobs.back")) && mouseY < y + 15)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        for (JobType jobType : JobType.values())
        {
            switch (jobType)
            {
                case HUNTER:
                    barX = (this.width - barWidth) / 2 - 82;
                    break;
                    
                case MINER:
                    barX = (this.width - barWidth) / 2;
                    break;
                    
                case LUMBERJACK:
                    barX = (this.width - barWidth) / 2 + 82;
                    break;
            }
            
            int textWidth = this.fontRendererObj.getStringWidth(I18n.format("gui.jobs." + jobType.name().toLowerCase()));
            int textX = barX + (barWidth - textWidth) / 2;
            int textY = barY + 36;
            
            if (mouseX >= textX && mouseX <= textX + textWidth && mouseY >= textY && mouseY <= textY + this.fontRendererObj.FONT_HEIGHT)
            {
                this.mc.displayGuiScreen(new UISubJobs(this, jobType));
            }
        }
        
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}