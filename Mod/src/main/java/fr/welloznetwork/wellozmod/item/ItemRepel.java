package fr.welloznetwork.wellozmod.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemRepel extends Item
{
	public static final int REPEL = 0, SUPER_REPEL = 1, MAX_REPEL = 2;
	private int type;
	
	public ItemRepel(String unlocalizedName, int type)
	{
		setMaxStackSize(16);
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setTextureName("wellozmod:" + unlocalizedName);
		this.type = type;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
	{
		if (GuiScreen.isShiftKeyDown())
		{
		    tooltip.remove(I18n.format("gui.tooltip.show"));
		    
		    switch (this.type)
		    {
		        case 0:
		            tooltip.add(I18n.format("repel1"));
		            tooltip.add(I18n.format("repel2") + " " + I18n.format("repel3"));
		            break;
		            
		        case 1:
		            tooltip.add(I18n.format("repel1"));
		            tooltip.add(I18n.format("repel2") + " " + I18n.format("repel4"));
		            break;
		            
		        case 2:
		            tooltip.add(I18n.format("repel1"));
		            tooltip.add(I18n.format("repel2") + " " + I18n.format("repel5"));
		    }
		}
		
		else
		{
		    tooltip.add(I18n.format("gui.tooltip.show"));
		}
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{	
		if (entityPlayer.inventory.consumeInventoryItem(this))
		{
		    switch (this.type) 
            {
                case 0:
                    this.repulsion(entityPlayer, 10.0D);
                    break;
                
                case 1:
                    this.repulsion(entityPlayer, 20.0D);
                    break;
                    
                case 2:
                    this.repulsion(entityPlayer, 30.0D);
                }
            }
		
		return itemStack;
	}
	
	public static void repulsion(EntityPlayer entityPlayer, double radius)
	{
	    AxisAlignedBB axisAlignedBB = AxisAlignedBB.getBoundingBox(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
	    List<EntityLivingBase> entityLivingBases = entityPlayer.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisAlignedBB.expand(radius, radius, radius));
	    
	    for (EntityLivingBase entityLivingBase : entityLivingBases)
	    {
	        if (entityLivingBase == entityPlayer)
	        {
	            continue;
	        }
	        
	        double offsetX = entityLivingBase.posX - entityPlayer.posX;
	        double offsetZ = entityLivingBase.posZ - entityPlayer.posZ;
	        
	        double moveX = offsetX / (Math.abs(offsetX) > Math.abs(offsetZ) ? Math.abs(offsetX) : Math.abs(offsetZ));
	        double moveZ = offsetZ / (Math.abs(offsetX) > Math.abs(offsetZ) ? Math.abs(offsetX) : Math.abs(offsetZ));
	        
	        entityLivingBase.motionX += moveX * 0.5D;
	        entityLivingBase.motionZ += moveZ * 0.5D;
	        entityLivingBase.velocityChanged = true;
	    }
	}
}