package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.modules.hud.client.ui.UIHUD;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class UIIngameMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final Object field_104025_t = new Object();
    private String field_92025_p, field_146972_A, reportBugs, sendFeedback;
    private int field_92024_r, field_92023_s, field_92022_t, field_92021_u, field_92020_v, field_92019_w, disconnectCounter = 0;
    
    public void initGui()
    {
        this.field_92025_p = "";
        this.reportBugs = "https://forms.gle/CcBz9eNLuj4T5msv8";
        this.sendFeedback = "https://forms.gle/tENkxEUUQgDZd5Qu7";
        this.buttonList.clear();
        
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + (byte) - 16, I18n.format("gui.customGuiIngameMenu.disconnected")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 24 + (byte) - 16, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96 + (byte) - 16, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 2, this.height / 4 + 96 + (byte) - 16, 98, 20, "HUD"));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, this.height / 4 + 72 + (byte) - 16, 98, 20, I18n.format("menu.reportBugs")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 72 + (byte) - 16, 98, 20, I18n.format("menu.sendFeedback")));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + (byte) - 16, 98, 20, I18n.format("gui.stats")));
        this.buttonList.add(new GuiButton(7, this.width / 2 - 100, this.height / 4 + 48 + (byte) - 16, 98, 20, I18n.format("gui.achievements")));
        
        Object object = this.field_104025_t;
        
        synchronized (this.field_104025_t)
        {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
            int j = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - j) / 2;
            this.field_92021_u = ((GuiButton) this.buttonList.get(0)).yPosition - 21;
            this.field_92020_v = this.field_92022_t + j;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }
    
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                this.mc.displayGuiScreen(new UIMainMenu());
                break;
                
            case 1:
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
                
            case 2:
                this.mc.displayGuiScreen(new UIOptions(this, this.mc.gameSettings));
                break;
                
            case 3:
                this.mc.displayGuiScreen(new UIHUD(this));
                break;
                
            case 4:
                this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.reportBugs, 0, true));
                break;
                
            case 5:
                this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.sendFeedback, 1, true));
                break;
                
            case 6:
                
                if (this.mc.thePlayer != null)
                {
                    this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                    break;
                }
                
            case 7:
                
                if (this.mc.thePlayer != null)
                {
                    this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                    break;
                }
        }
    }
    
    public void updateScreen()
    {
        GuiButton guiButton = (GuiButton) this.buttonList.get(0);
        this.disconnectCounter++;
        
        if (!this.mc.isSingleplayer())
        {
            if (this.disconnectCounter < 20)
            {
                guiButton.displayString = I18n.format("gui.customGuiIngameMenu.waiting1");
                guiButton.enabled = false;
            }
            
            else if (this.disconnectCounter > 20 && this.disconnectCounter < 40)
            {
                guiButton.displayString = I18n.format("gui.customGuiIngameMenu.waiting2");
                guiButton.enabled = false;
            }
            
            else if (this.disconnectCounter > 40 && this.disconnectCounter < 60)
            {
                guiButton.displayString = I18n.format("gui.customGuiIngameMenu.waiting3");
                guiButton.enabled = false;
            }
            
            else if (this.disconnectCounter > 60 && this.disconnectCounter < 80)
            {
                guiButton.displayString = I18n.format("gui.customGuiIngameMenu.waiting4");
                guiButton.enabled = false;
            }
            
            else if (this.disconnectCounter > 80 && this.disconnectCounter < 100)
            {
                guiButton.displayString = I18n.format("gui.customGuiIngameMenu.waiting5");
                guiButton.enabled = false;
            }
            
            else if (this.disconnectCounter > 100)
            {
                guiButton.displayString = I18n.format("gui.customGuiIngameMenu.disconnected");
                guiButton.enabled = true;
            }
        }
        
        else if (this.mc.isSingleplayer())
        {
            guiButton.displayString = I18n.format("menu.returnToMenu");
            guiButton.enabled = true;
        }
        
        super.updateScreen();
    }
    
    public void confirmClicked(boolean clicked, int button)
    {
        if (button == 0 && clicked)
        {
            try
            {
                Class oClass = Class.forName("java.awt.Desktop");
                Object object = oClass.getMethod("getDesktop", new Class[0]).invoke((Object) null);
                oClass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.reportBugs)});
            }
            
            catch (Throwable throwable)
            {
                LOGGER.error("Couldn\'t open link", throwable);
            }
        }
        
        else if (button == 1 && clicked)
        {
            try
            {
                Class oClass = Class.forName("java.awt.Desktop");
                Object object = oClass.getMethod("getDesktop", new Class[0]).invoke((Object) null);
                oClass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.sendFeedback)});
            }
            
            catch (Throwable throwable)
            {
                LOGGER.error("Couldn\'t open link", throwable);
            }
        }
        
        this.mc.displayGuiScreen(this);
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game"), this.width / 2, 40, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}