package fr.welloznetwork.wellozmod.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpyglass extends Item
{    
    public ItemSpyglass()
    {
        setUnlocalizedName("spyglass");
        setTextureName("wellozmod:spyglass");
        setMaxStackSize(1);
        setMaxDamage(3000);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setFull3D();
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
    {
        if (GuiScreen.isShiftKeyDown())
        {
            tooltip.remove(I18n.format("gui.tooltip.show"));
            
            tooltip.add(I18n.format("spyglass_1"));
            tooltip.add(I18n.format("spyglass_2"));
            tooltip.add(I18n.format("spyglass_3"));
        }
        
        else
        {
            tooltip.add(I18n.format("gui.tooltip.show"));
        }
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.SOUND_TYPE_SPYGLASS_USE, 1.0F, 1.0F);
        
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer entityPlayer, int time)
    {
        world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.SOUND_TYPE_SPYGLASS_STOP, 1.0F, 1.0F);
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
    }
    
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
        return 10000;
    }
    
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        return itemStack;
    }
    
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.bow;
    }
    
    public int getItemEnchantability()
    {
        return 1;
    }
}