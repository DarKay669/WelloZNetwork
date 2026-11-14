package fr.welloznetwork.wellozmod.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPotion extends Item
{ 
    private static final int FIRE = 0, GLOWING = 1, LEVITATION = 2, POISON = 3, STEP = 4, WATER_JUMP = 5, WITHER = 6;
	private PotionEffect potionEffect;
	private int type;
	
	public ItemPotion(String unlocalizedName, PotionEffect potionEffect, int type)
	{
		setMaxStackSize(1);
		setMaxDamage(0);
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:" + unlocalizedName);
		this.potionEffect = potionEffect;
		this.type = type;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
	{
	    switch (this.type)
	    {
	        case 0:
	            tooltip.add(I18n.format("potion_fire_description"));
	            break;
	            
	        case 1:
	            tooltip.add(I18n.format("potion_glowing_description"));
                break;
                
	        case 2:
	            tooltip.add(I18n.format("potion_levitation_description"));
                break;
                
	        case 3:
	            tooltip.add(I18n.format("potion_poison_description"));
                break;

	        case 4:
	            tooltip.add(I18n.format("potion_step_description"));
                break;

	        case 5:
	            tooltip.add(I18n.format("potion_water_jump_description"));
                break;
                
	        case 6:
	            tooltip.add(I18n.format("potion_wither_description"));
	            break;
	    }
	}
		
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (!entityPlayer.capabilities.isCreativeMode)
        {
            itemStack.stackSize--;
        }
        
        if (!world.isRemote)
        {
            entityPlayer.addPotionEffect(this.potionEffect);
        }
        
        if (!entityPlayer.capabilities.isCreativeMode)
        {
            if (itemStack.stackSize <= 0)
            {
                return new ItemStack(Items.glass_bottle);
            }
            
            entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
        
        return itemStack;
    }
	
	public int getMaxItemUseDuration(ItemStack itemStack)
    {
        return 32;
    }
    
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.drink;
    }
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
        return itemStack;
    }
	
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
	    return false;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemStack)
	{
	    return true;
	}
}