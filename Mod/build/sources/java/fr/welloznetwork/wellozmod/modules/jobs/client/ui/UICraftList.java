package fr.welloznetwork.wellozmod.modules.jobs.client.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.modules.jobs.eep.JobsEEP;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobData;
import fr.welloznetwork.wellozmod.modules.jobs.util.JobType;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UICraftList extends GuiScreen
{
    private GuiScreen parentScreen;
    private JobType jobType;
    private List<CraftEntry> entries;
    private int guiWidth = 256, guiHeight = 160, scrollOffset = 0, entryHeight = 26, visibleEntries = 4;
    
    public UICraftList(GuiScreen parentScreen, JobType jobType)
    {
        this.parentScreen = parentScreen;
        this.jobType = jobType;
        
        List<CraftEntry> craftEntries = Arrays.asList(
                new CraftEntry(new ItemStack(RegistryItems.zarux_sword), 20, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryItems.zarux_helmet), 20, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryItems.totem_of_undying), 10, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryItems.warped_fungus_on_a_stick), 5, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryItems.zombie_chestplate), 15, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryItems.zombie_leggings), 15, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryItems.zombie_boots), 15, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryBlocks.smithing_table), 5, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryBlocks.lapis_lamp), 5, JobType.HUNTER), 
                new CraftEntry(new ItemStack(RegistryBlocks.spectre_glass), 10, JobType.HUNTER), 
                
                new CraftEntry(new ItemStack(RegistryItems.zarux_pickaxe), 20, JobType.MINER), 
                new CraftEntry(new ItemStack(RegistryItems.zarux_chestplate), 20, JobType.MINER), 
                new CraftEntry(new ItemStack(RegistryItems.emerald_hammer), 10, JobType.MINER), 
                new CraftEntry(new ItemStack(RegistryItems.skeleton_chestplate), 15, JobType.MINER), 
                new CraftEntry(new ItemStack(RegistryItems.skeleton_leggings), 15, JobType.MINER), 
                new CraftEntry(new ItemStack(RegistryItems.skeleton_boots), 15, JobType.MINER), 
                new CraftEntry(new ItemStack(RegistryBlocks.scaffolding), 5, JobType.MINER), 
                new CraftEntry(new ItemStack(RegistryBlocks.quick_sand), 10, JobType.MINER), 
                
                new CraftEntry(new ItemStack(RegistryItems.zarux_axe), 20, JobType.LUMBERJACK), 
                new CraftEntry(new ItemStack(RegistryItems.zarux_leggings), 20, JobType.LUMBERJACK), 
                new CraftEntry(new ItemStack(RegistryItems.cat_paw_boots), 10, JobType.LUMBERJACK),
                new CraftEntry(new ItemStack(RegistryItems.creeper_chestplate), 15, JobType.LUMBERJACK), 
                new CraftEntry(new ItemStack(RegistryItems.creeper_leggings), 15, JobType.LUMBERJACK),
                new CraftEntry(new ItemStack(RegistryItems.creeper_boots), 15, JobType.LUMBERJACK), 
                new CraftEntry(new ItemStack(RegistryBlocks.slime_pad), 5, JobType.LUMBERJACK), 
                new CraftEntry(new ItemStack(RegistryBlocks.composter), 5, JobType.LUMBERJACK), 
                new CraftEntry(new ItemStack(RegistryBlocks.barrel), 5, JobType.LUMBERJACK),
                new CraftEntry(new ItemStack(RegistryBlocks.online_detector), 10, JobType.LUMBERJACK));
        
        this.entries = new ArrayList<CraftEntry>();
        
        for (CraftEntry craftEntry : craftEntries)
        {
            if (craftEntry.jobType == this.jobType)
            {
                this.entries.add(craftEntry);
            }
        }
        
        Collections.sort(this.entries, new Comparator<CraftEntry>()
        {
            public int compare(CraftEntry craftEntry, CraftEntry craftEntry1)
            {
                return Integer.compare(craftEntry.requiredLevel, craftEntry1.requiredLevel);
            }
        });
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
        
        GL11.glPopMatrix();
        
        int listX = x + 20, listY = y + 50;
        
        drawRect(listX, listY, listX + this.guiWidth - 40, listY + this.entryHeight * this.visibleEntries, 0xFF000000);
        
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
                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                
                this.drawHoveringText(Arrays.asList(jobData.getXpInCurrentLevel() + "/" + jobData.getXpForNextLevel() + " XP"), mouseX, mouseY, this.fontRendererObj);
                
                GL11.glPopAttrib();
            }
            
            List<CraftEntry> craftEntries = new ArrayList<CraftEntry>();
            
            for (CraftEntry craftEntry : this.entries)
            {
                if (craftEntry.jobType == this.jobType)
                {
                    craftEntries.add(craftEntry);
                }
            }
            
            int maxOffset = Math.max(craftEntries.size() - this.visibleEntries, 0);
            
            if (this.scrollOffset > maxOffset)
            {
                this.scrollOffset = maxOffset;
            }
            
            if (this.scrollOffset < 0)
            {
                this.scrollOffset = 0;
            }
            
            for (int i = 0; i < this.visibleEntries; i++)
            {
                if (i + this.scrollOffset >= craftEntries.size())
                {
                    break;
                }
                
                CraftEntry craftEntry = craftEntries.get(i + this.scrollOffset);
                int entryY = listY + 2 + i * this.entryHeight;
                
                if (craftEntry.itemStack != null)
                {
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    
                    itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), craftEntry.itemStack, listX + 5, entryY + 2);
                    
                    GL11.glPopAttrib();
                }
                
                this.fontRendererObj.drawString(craftEntry.itemStackName, listX + 25, entryY + 6, jobData.getLevel() >= craftEntry.requiredLevel ? Color.WHITE.getRGB() : Color.RED.getRGB());
                
                if (mouseX >= listX + 25 && mouseX <= listX + 25 + this.fontRendererObj.getStringWidth(craftEntry.itemStackName) && mouseY >= entryY && mouseY <= entryY + entryHeight)
                {
                    if (!(jobData.getLevel() >= craftEntry.requiredLevel))
                    {
                        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                        
                        this.drawHoveringText(Arrays.asList(craftEntry.itemStackName, EnumChatFormatting.RED + I18n.format("gui.jobs.levelRequiredCraft", new Object[] {craftEntry.requiredLevel})), mouseX, mouseY, this.fontRendererObj);
                        
                        GL11.glPopAttrib();
                    }
                }
            }
        }
        
        if (Math.max(this.entries.size() - this.visibleEntries, 0) > 0)
        {
            int barX = listX + this.guiWidth - 40 - 6;
            
            drawRect(barX, listY, barX + 6, listY + this.entryHeight * this.visibleEntries, 0xFF212121);
            
            int cursorY = listY + (int) ((float) this.scrollOffset / Math.max(this.entries.size() - this.visibleEntries, 0) * (this.entryHeight * this.visibleEntries - Math.max((int) ((float) this.visibleEntries / this.entries.size() * this.entryHeight * this.visibleEntries), 10)));
            
            drawRect(barX, cursorY, barX + 6, cursorY + Math.max((int) ((float) this.visibleEntries / this.entries.size() * this.entryHeight * this.visibleEntries), 10), 0xFFA1A1A1);
        }
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        int x = (this.width - this.guiWidth) / 2;
        int y = (this.height - this.guiHeight) / 2;
        
        if (mouseX >= x + 5 && mouseY >= y + 5 && mouseX < x + 5 + this.fontRendererObj.getStringWidth(I18n.format("gui.jobs.back")) && mouseY < y + 15)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void handleMouseInput()
    {
        int dWheel = Mouse.getDWheel();
        
        if (dWheel != 0)
        {
            if (dWheel > 0)
            {
                this.scrollOffset = Math.max(this.scrollOffset - 1, 0);
            }
            
            else if (dWheel < 0)
            {
                this.scrollOffset = Math.min(this.scrollOffset + 1, Math.max(this.entries.size() - this.visibleEntries, 0));
            }
        }
        
        super.handleMouseInput();
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    private class CraftEntry
    {
        private ItemStack itemStack;
        private String itemStackName;
        private int requiredLevel;
        private JobType jobType;
        
        private CraftEntry(ItemStack itemStack, String itemStackName, int requiredLevel, JobType jobType)
        {
            this.itemStack = itemStack;
            this.itemStackName = itemStackName;
            this.requiredLevel = requiredLevel;
            this.jobType = jobType;
        }
        
        private CraftEntry(ItemStack itemStack, int requiredLevel, JobType jobType)
        {
            this(itemStack, itemStack.getDisplayName(), requiredLevel, jobType);
        }
    }
}