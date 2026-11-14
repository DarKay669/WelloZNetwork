package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemArmorTurtle extends ItemArmor
{
	public ItemArmorTurtle()
	{
		super(RegistryArmorMaterials.TURTLE, 0, 0);
		setUnlocalizedName("turtle_helmet");
		setTextureName("wellozmod:turtle_helmet");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
	{
		if (slot == 2)
		{
			return "wellozmod:textures/models/armors/turtle_1.png";
		}
		
		return "wellozmod:textures/models/armors/turtle_1.png";
	}
	
	public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack)
	{
	    if (entityPlayer.isInWater())
	    {
	        entityPlayer.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 220, 0));
	        entityPlayer.triggerAchievement(RegistryAchievements.GOOD_SWIM);
	    }
	}
}