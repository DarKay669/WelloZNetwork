package fr.welloznetwork.wellozmod.item.food;

import cpw.mods.fml.common.eventhandler.Event;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class ItemChorusFruit extends ItemFood
{    
	public ItemChorusFruit()
	{
		super(4, 0.3F, false);
		setAlwaysEdible();
		setUnlocalizedName("chorus_fruit");
		setTextureName("wellozmod:chorus_fruit");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
	    ItemStack itemStack1 = super.onEaten(itemStack, world, entityPlayer);
	    
	    if (!world.isRemote)
	    {
	        double d0 = entityPlayer.posX;
	        double d1 = entityPlayer.posY;
	        double d2 = entityPlayer.posZ;
	            
	        for (int i = 0; i < 16; i++)
	        {
	            double d3 = entityPlayer.posX + (entityPlayer.getRNG().nextDouble() - 0.5D) * 16.0D;
	            double d4 = MathHelper.clamp_double(entityPlayer.posY + (double) (entityPlayer.getRNG().nextInt(16) - 8), 0.0D, (double) (world.getActualHeight() - 1));
	            double d5 = entityPlayer.posZ + (entityPlayer.getRNG().nextDouble() - 0.5D) * 16.0D;
	                
	            if (this.attemptTeleport(entityPlayer, d3, d4, d5))
	            {
	                entityPlayer.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
	                entityPlayer.playSound("mob.endermen.portal", 1.0F, 1.0F);
	                break;
	            }
	        }
	    }
	    
	    return itemStack1;
	}
	
	private boolean attemptTeleport(EntityLivingBase entityLivingBase, double x, double y, double z)
	{
		EnderTeleportEvent enderTeleportEvent = new EnderTeleportEvent(entityLivingBase, x, y, z, 0.0F);
		
		if (MinecraftForge.EVENT_BUS.post((Event) enderTeleportEvent))
		{
			return false;
		}
		
		double d3 = entityLivingBase.posX;
		double d4 = entityLivingBase.posY;
		double d5 = entityLivingBase.posZ;
		entityLivingBase.posX = enderTeleportEvent.targetX;
		entityLivingBase.posY = enderTeleportEvent.targetY;
		entityLivingBase.posZ = enderTeleportEvent.targetZ;
		boolean flag = false;
		int i = MathHelper.floor_double(entityLivingBase.posX);
		int j = MathHelper.floor_double(entityLivingBase.posY);
		int k = MathHelper.floor_double(entityLivingBase.posZ);
		
		if (entityLivingBase.worldObj.blockExists(i, j, k))
		{
			boolean flag1 = false;
			
			while (!flag1 && j > 0)
			{
				Block block = entityLivingBase.worldObj.getBlock(i, j - 1, k);
				
				if (block.getMaterial().blocksMovement())
				{
					flag1 = true;
					continue;
				}
				
				entityLivingBase.posY--;
				j--;
			}
			
			if (flag1)
			{
				entityLivingBase.setPositionAndUpdate(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
				
				if (entityLivingBase.worldObj.getCollidingBoundingBoxes((Entity) entityLivingBase, entityLivingBase.boundingBox).isEmpty() && !entityLivingBase.worldObj.isAnyLiquid(entityLivingBase.boundingBox))
				{
					flag = true;
				}
			}
		}
		
		if (!flag)
		{
			entityLivingBase.setPosition(d3, d4, d5);
			return false;
		}
		
		short short1 = 128;
		
		for (int l = 0; l < short1; l++)
		{
			double d6 = l / (short1 - 1.0D);
			float f = (entityLivingBase.getRNG().nextFloat() - 0.5F) * 0.2F;
			float f1 = (entityLivingBase.getRNG().nextFloat() - 0.5F) * 0.2F;
			float f2 = (entityLivingBase.getRNG().nextFloat() - 0.5F) * 0.2F;
			double d7 = d3 + (entityLivingBase.posX - d3) * d6 + (entityLivingBase.getRNG().nextDouble() - 0.5D) * entityLivingBase.width * 2.0D;
			double d8 = d4 + (entityLivingBase.posY - d4) * d6 + entityLivingBase.getRNG().nextDouble() * entityLivingBase.height;
			double d9 = d5 + (entityLivingBase.posZ - d5) * d6 + (entityLivingBase.getRNG().nextDouble() - 0.5D) * entityLivingBase.width * 2.0D;
			entityLivingBase.worldObj.spawnParticle("portal", d7, d8, d9, f, f1, f2);
		}
		
		return true;
	}
}