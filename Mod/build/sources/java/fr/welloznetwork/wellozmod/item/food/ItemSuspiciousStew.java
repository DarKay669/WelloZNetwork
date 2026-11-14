package fr.welloznetwork.wellozmod.item.food;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemSuspiciousStew extends ItemFood
{
    private int type;
    
    public ItemSuspiciousStew(int type)
    {
        super(0, false);
        setAlwaysEdible();
        setMaxStackSize(1);
        setUnlocalizedName("suspicious_stew");
        setTextureName("wellozmod:suspicious_stew");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.type = type;
    }
    
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        super.onEaten(itemStack, world, entityPlayer);
        
        switch (this.type) 
        {
            case 0:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 140, 0));
                break;
                
            case 1:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 140, 0));
                break;
                
            case 2:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 120, 0));
                break;
                
            case 3:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.id, 200, 0));
                break;
                
            case 4:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.id, 210, 0));
                break;
                
            case 5:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
                break;
                
            case 6:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.jump.id, 160, 0));
                break;
                
            case 7:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 280, 0));
                break;
                
            case 8:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.id, 200, 0));
        }
        
        return new ItemStack(Items.bowl);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
    {        
        switch (this.type) 
        {
            case 0:
                tooltip.add(EnumChatFormatting.BLUE + I18n.format("suspicious_stew_saturation"));
                break;
                
            case 1:
                tooltip.add(EnumChatFormatting.BLUE + I18n.format("suspicious_stew_night_vision") + " (00:05)");
                break;
                
            case 2:
                tooltip.add(EnumChatFormatting.BLUE + I18n.format("suspicious_stew_fire_resistance") + " (00:04)");
                break;
                
            case 3:
                tooltip.add(EnumChatFormatting.RED + I18n.format("suspicious_stew_blindness") + " (00:08)");
                break;
                
            case 4:
                tooltip.add(EnumChatFormatting.RED + I18n.format("suspicious_stew_weakness1") + " (00:09)");
                tooltip.add("");
                tooltip.add(EnumChatFormatting.DARK_PURPLE + I18n.format("suspicious_stew_weakness2"));
                tooltip.add(EnumChatFormatting.RED + I18n.format("suspicious_stew_weakness3"));
                break;
                
            case 5:
                tooltip.add(EnumChatFormatting.BLUE + I18n.format("suspicious_stew_regeneration") + " (00:08)");
                break;
                
            case 6:
                tooltip.add(EnumChatFormatting.BLUE + I18n.format("suspicious_stew_jump") + " (00:06)");
                break;
                
            case 7:
                tooltip.add(EnumChatFormatting.RED + I18n.format("suspicious_stew_poison") + " (00:12)");
                break;
                
            case 8:
                tooltip.add(EnumChatFormatting.RED + I18n.format("suspicious_stew_wither") + " (00:08)");
        }
    }
}