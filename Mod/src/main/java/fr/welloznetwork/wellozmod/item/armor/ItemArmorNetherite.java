package fr.welloznetwork.wellozmod.item.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorNetherite extends ItemArmor
{    
	public ItemArmorNetherite(String unlocalizedName, int type)
	{
		super(RegistryArmorMaterials.NETHERITE, 0, type);
		setUnlocalizedName(unlocalizedName);
		setTextureName("wellozmod:" + unlocalizedName);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
    {
        if (repair.getItem() == RegistryItems.netherite && repair.getItemDamage() == 0)
        {
            return true;
        }
        
        return false;
    }
	
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
	{
		if (slot == 2)
		{
			return "wellozmod:textures/models/armors/netherite_2.png";
		}
		
		return "wellozmod:textures/models/armors/netherite_1.png";
	}
	
	public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack)
	{
	    if (entityPlayer.getEquipmentInSlot(1) != null && entityPlayer.getEquipmentInSlot(2) != null && entityPlayer.getEquipmentInSlot(3) != null && entityPlayer.getEquipmentInSlot(4) != null)
	    {
	        entityPlayer.triggerAchievement(RegistryAchievements.COVER_ME_WITH_DEBRIS);
	    }
	}
	
	public Multimap getItemAttributeModifiers()
	{
	    final Multimap multimap = (Multimap) HashMultimap.create();
	    multimap.put((Object) SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), (Object) new AttributeModifier("netheriteArmorModifier", 1.0D, 0));
	    return multimap;
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
	    int i = EntityLiving.getArmorPosition(itemStack) - 1;
	    
	    if (entityPlayer.getCurrentArmor(i) == null)
	    {
	        entityPlayer.setCurrentItemOrArmor(i + 1, itemStack.copy());
	        itemStack.stackSize = 0;
	        world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_ARMOR_EQUIP_NETHERITE[world.rand.nextInt(RegistrySounds.ITEM_ARMOR_EQUIP_NETHERITE.length)], 1.0F, 1.0F);
	    }
	    
	    return itemStack;
	}
}