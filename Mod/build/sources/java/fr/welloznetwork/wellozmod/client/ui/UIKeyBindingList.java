package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

@SideOnly(Side.CLIENT)
public class UIKeyBindingList extends GuiListExtended
{
    private final UIControls controlsScreen;
    private final Minecraft mc;
    private IGuiListEntry[] listEntries;
    private int maxListLabelWidth;
    
    public UIKeyBindingList(UIControls controls, Minecraft mcIn)
    {
        super(mcIn, controls.width + 45, controls.height, 63, controls.height - 32, 20);

        this.controlsScreen = controls;
        this.mc = mcIn;
        KeyBinding[] keyBindings = (KeyBinding[]) ArrayUtils.clone(mcIn.gameSettings.keyBindings);
        this.listEntries = new IGuiListEntry[keyBindings.length + KeyBinding.getKeybinds().size()];
        Arrays.sort((Object[]) keyBindings);
        int i = 0;
        String s = null;
        
        for (KeyBinding keyBinding : keyBindings)
        {
            String s1 = keyBinding.getKeyCategory();
            
            if (!s1.equals(s))
            {
                s = s1;
                this.listEntries[i++] = new CategoryEntry(s1);
            }
            
            int j = this.mc.fontRenderer.getStringWidth(I18n.format(keyBinding.getKeyDescription()));
            
            if (j > this.maxListLabelWidth)
            {
                this.maxListLabelWidth = j;
            }
            
            this.listEntries[i++] = new KeyEntry(keyBinding);
        }
    }
    
    public IGuiListEntry getListEntry(int index)
    {
        return this.listEntries[index];
    }
    
    protected int getSize()
    {
        return this.listEntries.length;
    }
    
    protected int getScrollBarX()
    {
        return super.getScrollBarX() + 15;
    }
    
    private class CategoryEntry implements IGuiListEntry
    {
        private final String labelText;
        private final int labelWidth;
        
        public CategoryEntry(String name)
        {
            this.labelText = I18n.format(name);
            this.labelWidth = UIKeyBindingList.this.mc.fontRenderer.getStringWidth(this.labelText);
        }
        
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected)
        {
            UIKeyBindingList.this.mc.fontRenderer.drawString(this.labelText, UIKeyBindingList.this.mc.currentScreen.width / 2 - this.labelWidth / 2, y + slotHeight - UIKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT - 1, Color.WHITE.getRGB());
        }
        
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
        {
            return false;
        }
        
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
    }
    
    private class KeyEntry implements IGuiListEntry
    {
        private final KeyBinding keyBinding;
        private final String keyDesc;
        private final GuiButton btnChangeKeyBinding;
        private final GuiButton btnReset;
        
        private KeyEntry(KeyBinding name)
        {
            this.keyBinding = name;
            this.keyDesc = I18n.format(name.getKeyDescription());
            this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(name.getKeyDescription()));
            this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset"));
        }
        
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, Tessellator tessellator, int mouseX, int mouseY, boolean isSelected)
        {
            boolean flag = UIKeyBindingList.this.controlsScreen.buttonId == this.keyBinding;
            
            UIKeyBindingList.this.mc.fontRenderer.drawString(this.keyDesc, x + 90 - UIKeyBindingList.this.maxListLabelWidth, y + slotHeight / 2 - UIKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT / 2, Color.WHITE.getRGB());
            
            this.btnReset.xPosition = x + 190;
            this.btnReset.yPosition = y;
            this.btnReset.enabled = this.keyBinding.getKeyCode() != this.keyBinding.getKeyCodeDefault();
            this.btnReset.drawButton(UIKeyBindingList.this.mc, mouseX, mouseY);
            
            this.btnChangeKeyBinding.xPosition = x + 105;
            this.btnChangeKeyBinding.yPosition = y;
            this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keyBinding.getKeyCode());
            
            boolean flag1 = false;
            
            if (this.keyBinding.getKeyCode() != 0)
            {
                for (KeyBinding keyBinding : UIKeyBindingList.this.mc.gameSettings.keyBindings)
                {
                    if (keyBinding != this.keyBinding && keyBinding.getKeyCode() == this.keyBinding.getKeyCode())
                    {
                        flag1 = true;
                        break;
                    }
                }
            }
            
            if (flag)
            {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
            }
            
            else if (flag1)
            {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
            }
            
            this.btnChangeKeyBinding.drawButton(UIKeyBindingList.this.mc, mouseX, mouseY);
        }
        
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
        {
            if (this.btnChangeKeyBinding.mousePressed(UIKeyBindingList.this.mc, mouseX, mouseY))
            {
                UIKeyBindingList.this.controlsScreen.buttonId = this.keyBinding;
                return true;
            }
            
            else if (this.btnReset.mousePressed(UIKeyBindingList.this.mc, mouseX, mouseY))
            {
                UIKeyBindingList.this.mc.gameSettings.setOptionKeyBinding(this.keyBinding, this.keyBinding.getKeyCodeDefault());
                KeyBinding.resetKeyBindingArrayAndHash();
                return true;
            }
            
            else
            {
                return false;
            }
        }
        
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) 
        {
            this.btnChangeKeyBinding.mouseReleased(x, y);
            this.btnReset.mouseReleased(x, y);
        }
    }
}