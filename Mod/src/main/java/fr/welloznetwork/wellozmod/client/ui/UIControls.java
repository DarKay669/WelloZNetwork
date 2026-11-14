package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.client.settings.KeyBinding;

@SideOnly(Side.CLIENT)
public class UIControls extends GuiScreen
{
    private static final Options[] OPTIONS_ARR = new Options[] {Options.INVERT_MOUSE, Options.SENSITIVITY};
    private final GuiScreen parentScreen;
    protected String screenTitle;
    private final GameSettings options;
    public KeyBinding buttonId;
    public long time;
    private UIKeyBindingList keyBindingList;
    private GuiButton buttonReset;
    
    public UIControls(GuiScreen screen, GameSettings settings)
    {
        this.parentScreen = screen;
        this.options = settings;
    }
    
    public void initGui()
    {
        this.keyBindingList = new UIKeyBindingList(this, this.mc);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 155 + 160, this.height - 29, 150, 20, I18n.format("gui.done")));
        this.buttonList.add(this.buttonReset = new GuiButton(201, this.width / 2 - 155, this.height - 29, 150, 20, I18n.format("controls.resetAll")));
        this.screenTitle = I18n.format("controls.title");
        int i = 0;
        
        for (Options options : OPTIONS_ARR)
        {
            if (options.getEnumFloat())
            {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), options));
            }
            
            else
            {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), options, this.options.getKeyBinding(options)));
            }
            
            i++;
        }
    }
    
    public void handleMouseInput()
    {
        super.handleMouseInput();
    }
    
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 200)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        
        else if (button.id == 201)
        {
            for (KeyBinding keyBinding : this.mc.gameSettings.keyBindings)
            {
                keyBinding.setKeyCode(keyBinding.getKeyCodeDefault());
            }
            
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        
        else if (button.id < 100 && button instanceof GuiOptionButton)
        {
            this.options.setOptionValue(((GuiOptionButton) button).returnEnumOptions(), 1);
            button.displayString = this.options.getKeyBinding(Options.getEnumOptions(button.id));
        }
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (this.buttonId != null)
        {
            this.options.setOptionKeyBinding(this.buttonId, -100 + mouseButton);
            this.buttonId = null;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        
        else if (mouseButton != 0 || !this.keyBindingList.func_148179_a(mouseX, mouseY, mouseButton))
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    protected void mouseMovedOrUp(int mouseX, int mouseY, int state)
    {
        if (state != 0 || !this.keyBindingList.func_148181_b(mouseX, mouseY, state))
        {
            super.mouseMovedOrUp(mouseX, mouseY, state);
        }
    }
    
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.buttonId != null)
        {
            if (keyCode == 1)
            {
                this.options.setOptionKeyBinding(this.buttonId, 0);
            }
            
            else if (keyCode != 0)
            {
                this.options.setOptionKeyBinding(this.buttonId, keyCode);
            }
            
            else if (typedChar > 0)
            {
                this.options.setOptionKeyBinding(this.buttonId, typedChar + 256);
            }
            
            this.buttonId = null;
            this.time = Minecraft.getSystemTime();
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 8, Color.WHITE.getRGB());
        boolean flag = false;
        
        for (KeyBinding keyBinding : this.options.keyBindings)
        {
            if (keyBinding.getKeyCode() != keyBinding.getKeyCodeDefault())
            {
                flag = true;
                break;
            }
        }
        
        this.buttonReset.enabled = flag;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}