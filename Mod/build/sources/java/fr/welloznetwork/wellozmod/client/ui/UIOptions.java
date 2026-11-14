package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.net.URI;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.mixins.client.gui.IMixinFontRendererAccessor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiSnooper;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.gui.stream.GuiStreamOptions;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UIOptions extends GuiScreen
{
    private static final Options[] SCREEN_OPTIONS = new Options[] {Options.FOV, Options.DIFFICULTY};
    private final GuiScreen lastScreen;
    private final GameSettings gameSettings;
    private String title = "Options";
    public static boolean newFont = false;
    
    public UIOptions(GuiScreen guiScreen, GameSettings gameSettings)
    {
        this.lastScreen = guiScreen;
        this.gameSettings = gameSettings;
    }
    
    public void initGui()
    {
        int i = 0;
        this.title = I18n.format("options.title");
        Options[] aOptions = SCREEN_OPTIONS;
        int j = aOptions.length;
        
        for (int k = 0; k < j; k++)
        {
            Options options = aOptions[k];
            
            if (options.getEnumFloat())
            {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), options));
            }
            
            else
            {
                GuiOptionButton difficultyButton = new GuiOptionButton(options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), options, this.gameSettings.getKeyBinding(options));
                
                if (options == Options.DIFFICULTY && this.mc.isSingleplayer() && !this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
                {
                    difficultyButton.enabled = true;
                    difficultyButton.displayString = I18n.format("options.difficulty") + ": " + I18n.format("options.difficulty.hardcore");
                }
                
                else
                {
                    difficultyButton.enabled = false;
                }
                
                this.buttonList.add(difficultyButton);
            }
            
            i++;
        }
        
        this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.video")));
        this.buttonList.add(new GuiButton(102, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.language")));
        this.buttonList.add(new GuiButton(103, this.width / 2 - 155, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack")));
        this.buttonList.add(new GuiButton(104, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done")));
        this.buttonList.add(new GuiButton(107, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.multiplayer.title")));
        this.buttonList.add(new GuiButton(108, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls")));
        this.buttonList.add(new GuiButton(109, this.width / 2 + 5, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view")));
        this.buttonList.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds")));
        this.buttonList.add(new GuiButton(111, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "Super Secret Settings..."));
        this.buttonList.add(new GuiButton(112, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.stream")));
        
        this.buttonList.add(new GuiButton(113, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("gui.supportTheServer"))
        {
            public void mouseReleased(int mouseX, int mouseY)
            {
                if (Desktop.isDesktopSupported())
                {
                    try
                    {
                        Desktop.getDesktop().browse(new URI("https://www.paypal.com/paypalme/prelistommy"));
                    }
                    
                    catch (Exception e) {}
                }
            }
        });
    }
    
    protected void actionPerformed(GuiButton guiButton)
    {
        if (guiButton.enabled)
        {
            if (guiButton.id < 100 && guiButton instanceof GuiOptionButton)
            {
                this.gameSettings.setOptionValue(((GuiOptionButton) guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.gameSettings.getKeyBinding(Options.getEnumOptions(guiButton.id));
            }
            
            else if (guiButton.id == 101)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiVideoSettings(this, this.gameSettings));
            }
            
            else if (guiButton.id == 102)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new UILanguage(this, this.gameSettings, this.mc.getLanguageManager()));
            }
            
            else if (guiButton.id == 103)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }
            
            else if (guiButton.id == 104)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.lastScreen);
            }
            
            else if (guiButton.id == 107)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new ScreenChatOptions(this, this.gameSettings));
            }
            
            else if (guiButton.id == 108)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new UIControls(this, this.gameSettings));
            }
            
            else if (guiButton.id == 109)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiSnooper(this, this.gameSettings));
            }
            
            else if (guiButton.id == 110)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.gameSettings));
            }
            
            else if (guiButton.id == 111)
            {
                if (!newFont)
                {
                    ((IMixinFontRendererAccessor) this.mc.fontRenderer).setResourceLocation(new ResourceLocation("wellozmod:textures/font/sm64.png"));
                }
                
                else
                {
                    ((IMixinFontRendererAccessor) this.mc.fontRenderer).setResourceLocation(new ResourceLocation("minecraft:textures/font/ascii.png"));
                }
                
                this.mc.gameSettings.saveOptions();
                newFont = !newFont;
            }
            
            else if (guiButton.id == 112)
            {
                this.mc.gameSettings.saveOptions();
                
                IStream iStream = this.mc.func_152346_Z();
                
                if (iStream.func_152936_l() && iStream.func_152928_D())
                {
                    this.mc.displayGuiScreen(new GuiStreamOptions(this, this.gameSettings));
                }
                
                else
                {
                    GuiStreamUnavailable.func_152321_a(this);
                }
            }
        }
    }
        
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, Color.WHITE.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}