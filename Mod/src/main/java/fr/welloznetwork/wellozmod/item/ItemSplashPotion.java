package fr.welloznetwork.wellozmod.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.projectile.EntitySplashPotion;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSplashPotion extends Item
{
    private PotionEffect potionEffect;
    private String[] potionType = new String[] {"sickness_splash_potion", "web_splash_potion", "explosive_splash_potion", "darkness_splash_potion"};
    private IIcon[] iconArray;
    
    public ItemSplashPotion(String unlocalizedName, String textureName, PotionEffect potionEffect, int damageValue)
    {
        setMaxStackSize(1);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setHasSubtypes(true);
        this.potionEffect = potionEffect;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
    {
        switch (getDamage(itemStack))
        {
            case 0:
                tooltip.add(EnumChatFormatting.RED + I18n.format("splash_potion_sickness_description"));
                break;
                
            case 1:
                tooltip.add(I18n.format("splash_potion_web_description"));
                break;
                
            case 2:
                tooltip.add(I18n.format("splash_potion_explosive_description"));
                break;
                
            case 3:
                tooltip.add(EnumChatFormatting.RED + I18n.format("splash_potion_darkness_description"));
                break;
        }
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (!world.isRemote)
        {
            world.spawnEntityInWorld((Entity) new EntitySplashPotion(world, entityPlayer, getDamage(itemStack)));
            itemStack.stackSize--;
        }
        
        return itemStack;
    }
    
    public int getMetadata(int metadata)
    {
        return metadata;
    }
    
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int metadata = itemStack.getItemDamage();
        
        if (metadata > this.potionType.length || metadata < 0)
        {
            metadata = 0;
        }
        
        return "item." + this.potionType[metadata].toLowerCase();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iiconRegister)
    {
        this.iconArray = new IIcon[this.potionType.length];
        
        for (int i = 0; i < this.potionType.length; i++)
        {
            this.iconArray[i] = iiconRegister.registerIcon("wellozmod:" + this.potionType[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int metadata = 0; metadata < this.potionType.length; metadata++)
        {
            list.add(new ItemStack(item, 1, metadata));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int metadata)
    {
        return (metadata < this.potionType.length && metadata >= 0) ? this.iconArray[metadata] : this.iconArray[0];
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack)
    {
        return true;
    }
}