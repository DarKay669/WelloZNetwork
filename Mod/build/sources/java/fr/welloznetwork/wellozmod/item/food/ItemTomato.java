package fr.welloznetwork.wellozmod.item.food;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemTomato extends ItemFood
{
    public ItemTomato()
    {
        super(6, false);
        setUnlocalizedName("tomato");
        setTextureName("wellozmod:tomato");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
    {
        if (GuiScreen.isShiftKeyDown())
        {
            tooltip.remove(I18n.format("gui.tooltip.show"));
            
            tooltip.add(I18n.format("tomato"));
        }
        
        else
        {
            tooltip.add(I18n.format("gui.tooltip.show"));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack)
    {
        return true;
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.epic;
    }
}