package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockSlimePad extends BlockBreakable
{
	public BlockSlimePad()
	{
		super("wellozmod:slime_block", Material.clay, false);
		slipperiness = 0.8F;
		setBlockName("slime_pad");
		setBlockTextureName("wellozmod:slime_block");
		setBlockBounds(0.125F, 0.0625F, 0.125F, 0.875F, 0.625F, 0.875F);
		setResistance(8.0F);
		setHarvestLevel("pickaxe", 2);
		setHardness(12.0F);
		setStepSound(soundTypeCloth);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
	    return 1;
	}
	
	protected boolean canSilkHarvest()
	{
	    return true;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z, Entity entity)
	{
		return null;
	}
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (!world.isBlockIndirectlyGettingPowered(x, y, z))
		{
			double moveX = 0.0D, moveZ = 0.0D, speed = 0.25D;
			int meta = world.getBlockMetadata(x, y, z);
			
			switch (meta % 8)
			{
    			case 6:
    				moveX += speed;
    				break;
    				
    			case 7:
    			    
    				moveX += speed;
    				moveZ += speed;
    				break;
    				
    			case 0:
    				moveZ += speed;
    				break;
    				
    			case 1:
    				moveZ += speed;
    				moveX -= speed;
    				break;
    				
    			case 2:
    				moveX -= speed;
    				break;
    				
    			case 3:
    				moveX -= speed;
    				moveZ -= speed;
    				break;
    				
    			case 4:
    				moveZ -= speed;
    				break;
    				
    			case 5:
    				moveZ -= speed;
    				moveX += speed;
			}
			
			if ((entity instanceof EntityItem))
			{
				entity.posY += 1.0D;
			}
			
			entity.fallDistance = 0.0F;
			entity.addVelocity(moveX, speed * 2.0D, moveZ);
		}
	}
	
	public boolean isBlockReplaceable(World world, int x, int y, int z)
	{
		return false;
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
	{
		int face = MathHelper.floor_double(entityLivingBase.rotationYaw * 8.0F / 360.0F + 0.5D) + (entityLivingBase.isSneaking() ? 4 : 0) & 0x7;
		
		int meta = world.getBlockMetadata(x, y, z) & 0x8;
		world.setBlockMetadataWithNotify(x, y, z, face | meta, 2);
	}
}