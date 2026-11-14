package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryUI;
import fr.welloznetwork.wellozmod.tileentity.TileEntityEmeraldChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockEmeraldChest extends BlockContainer
{
	public BlockEmeraldChest()
	{
		super(Material.iron);
		setHardness(5.0F);
		setResistance(10.0F);
		setHarvestLevel("pickaxe", 2);
		setBlockName("emerald_chest");
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setStepSound(soundTypeMetal);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityEmeraldChest();
	}
	
	public boolean hasTileEntity()
	{
		return true;
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		byte b0 = 0;
		int l = MathHelper.floor_double((entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3;
		
		if (l == 0)
		{
			b0 = 2;
		}
		
		if (l == 1) 
		{
			b0 = 5;
		}
		
		if (l == 2)
		{
			b0 = 3;
		}
		
		if (l == 3)
		{
			b0 = 4;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		
		((EntityPlayer) entity).triggerAchievement(RegistryAchievements.MORE_STORAGE);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.getBlock(x, y + 1, z).isNormalCube())
		{
			if (!world.isRemote)
			{
				player.openGui(WelloZMod.instance, RegistryUI.EMERALD_CHEST, world, x, y, z);
			}
			
			return true;
		}
		
		return true;
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		
		if ((tileentity instanceof IInventory))
		{
			IInventory inv = (IInventory) tileentity;
			
			for (int i1 = 0; i1 < inv.getSizeInventory(); i1++)
			{
				ItemStack itemstack = inv.getStackInSlot(i1);
				
				if (itemstack != null)
				{
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					
					EntityItem entityitem;
					
					for (float f2 = world.rand.nextFloat() * 0.8F + 1.0F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))		
					{
						int j1 = world.rand.nextInt(21) + 10;
						
						if (j1 > itemstack.stackSize)
						{
							j1 = itemstack.stackSize;
						}
						
						itemstack.stackSize -= j1;
						
						entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = ((float) world.rand.nextGaussian() * f3);
						entityitem.motionY = ((float) world.rand.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = ((float) world.rand.nextGaussian() * f3);
						
						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}
			
			world.notifyBlockOfNeighborChange(x, y, z, block);
		}
		
		super.breakBlock(world, x, y, z, block, metadata);
	}
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public int getRenderType()
	{
		return 22;
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iiconRegister)
	{
	    this.blockIcon = iiconRegister.registerIcon("emerald_block");
	}
}