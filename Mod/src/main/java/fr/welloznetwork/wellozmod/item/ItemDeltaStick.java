package fr.welloznetwork.wellozmod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemDeltaStick extends Item
{
	public ItemDeltaStick()
	{
		setUnlocalizedName("delta_stick");
		setTextureName("wellozmod:delta_stick");
		setMaxStackSize(1);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemStack, int pass)
	{
		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
	    itemStack.damageItem(1, entityPlayer);
	    
	    entityPlayer.heal(6.0F);
	    entityPlayer.setAbsorptionAmount(6.0F);
	    entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1300, 4));
	    entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(RegistryPotions.WITHER.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(RegistryPotions.FIRE.id, 1300, 4));
        entityPlayer.addPotionEffect(new PotionEffect(RegistryPotions.POISON.id, 1300, 4));
        
        world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + world.rand.nextFloat() * 0.2F);
        world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, "random.explode", 2.0F, 0.5F + world.rand.nextFloat() * 0.2F);
        
	    for (int i = 0; i < 200; i++)
	    {
	        world.spawnParticle("reddust", entityPlayer.posX + world.rand.nextDouble() * 2.0D - 1.0D, entityPlayer.posY + world.rand.nextDouble() * 2.0D - 2.0D, entityPlayer.posZ + world.rand.nextDouble() * 2.0D - 1.0D, 255.0D, 0.0D, 0.0D);
	    }
			
	    itemStack.stackSize--;
	    
		return itemStack;
	}
	
	public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.rare;
    }
}