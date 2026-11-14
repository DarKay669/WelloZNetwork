package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistryPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemArmorZarux extends ItemArmor
{	
	public ItemArmorZarux(String unlocalizedName, int type)
	{
		super(RegistryArmorMaterials.ZARUX, 0, type);
		setUnlocalizedName(unlocalizedName);
		setTextureName("wellozmod:" + unlocalizedName);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
	{
		if (slot == 2)
		{
			return "wellozmod:textures/models/armors/zarux_2.png";
		}
		
		return "wellozmod:textures/models/armors/zarux_1.png";
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
		if (repair.getItem() == RegistryItems.zarux)
		{
			return true;
		}
		
		return false;
	}
	
	public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack)
	{
		switch (this.armorType)
		{
    		case 0:
    		    entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, 1));
    			break;
    			
    		case 1:
    		    entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 50, 1));
    			break;
    			
    		case 2:
    		    
    			if ((entityPlayer.getCurrentArmor(1) != null) && (entityPlayer.getCurrentArmor(2) != null) && (entityPlayer.getCurrentArmor(3) != null) && ((entityPlayer.getCurrentArmor(1).getItem() instanceof ItemArmorZarux)) && ((entityPlayer.getCurrentArmor(2).getItem() instanceof ItemArmorZarux)) && ((entityPlayer.getCurrentArmor(3).getItem() instanceof ItemArmorZarux)))
    			{
    			    entityPlayer.addPotionEffect(new PotionEffect(RegistryPotions.FIRE.id, 10, 256));
    			}
    			
    			break;
    			
    		case 3:
    		    entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 50, 1));
		}
	}
}