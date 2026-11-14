package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

@SideOnly(Side.CLIENT)
public class UILanguage extends GuiScreen
{
    private GuiScreen parentScreen;
    private List list;
    private final GameSettings gameSettings;
    private final LanguageManager languageManager;
    private GuiButton confirmSettingsButton;
    
    public UILanguage(GuiScreen guiScreen, GameSettings gameSettings, LanguageManager languageManager)
    {
        this.parentScreen = guiScreen;
        this.gameSettings = gameSettings;
        this.languageManager = languageManager;
    }
    
    public void initGui()
    {        
        this.buttonList.add(this.confirmSettingsButton = new GuiButton(0, this.width / 2 - 100, this.height - 38, I18n.format("gui.done")));
        
        this.list = new List();
        this.list.registerScrollButtons(7, 8);
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton.enabled)
        {
            switch (guiButton.id)
            {
                case 0:
                    this.mc.displayGuiScreen(this.parentScreen);
                    this.mc.refreshResources();
                    break;
                
                default:
                    this.list.actionPerformed(guiButton);
            }
        }
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        
        this.drawCenteredString(this.fontRendererObj, I18n.format("options.language"), this.width / 2, 16, Color.WHITE.getRGB());
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @SideOnly(Side.CLIENT)
    class List extends GuiSlot
    {
        private final java.util.List languageCodeList = new ArrayList<>();
        private final Map languageMap = Maps.newHashMap();

        public List()
        {
            super(UILanguage.this.mc, UILanguage.this.width, UILanguage.this.height, 32, UILanguage.this.height - 65 + 4, 18);
            Iterator<Language> iterator = UILanguage.this.languageManager.getLanguages().iterator();

            while (iterator.hasNext())
            {
                Language language = iterator.next();
                
                if ("en_US".equals(language.getLanguageCode()) || "fr_FR".equals(language.getLanguageCode()))
                {
                    this.languageMap.put(language.getLanguageCode(), language);
                    this.languageCodeList.add(language.getLanguageCode());
                }
            }
        }
        
        protected int getSize()
        {
            return this.languageCodeList.size();
        }
        
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
        {
            Language language = (Language) this.languageMap.get(this.languageCodeList.get(slotIndex));
            
            UILanguage.this.languageManager.setCurrentLanguage(language);
            UILanguage.this.gameSettings.language = language.getLanguageCode();
            UILanguage.this.confirmSettingsButton.displayString = I18n.format("gui.done");
            UILanguage.this.gameSettings.saveOptions();
        }
        
        protected boolean isSelected(int slotIndex)
        {
            return ((String) this.languageCodeList.get(slotIndex)).equals(UILanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
        }
        
        protected int getContentHeight()
        {
            return this.getSize() * 18;
        }

        protected void drawBackground()
        {
            UILanguage.this.drawDefaultBackground();
        }

        protected void drawSlot(int entryID, int insideLeft, int yPos, int insideSlotHeight, Tessellator tessellator, int mouseX, int mouseY)
        {
            UILanguage.this.drawCenteredString(UILanguage.this.fontRendererObj, ((Language) this.languageMap.get(this.languageCodeList.get(entryID))).toString(), this.width / 2, yPos + 1, Color.WHITE.getRGB());
        }
    }
}