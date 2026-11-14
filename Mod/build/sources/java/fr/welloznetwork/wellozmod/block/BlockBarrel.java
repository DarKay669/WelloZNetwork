package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import fr.welloznetwork.wellozmod.init.RegistryUI;
import fr.welloznetwork.wellozmod.tileentity.TileEntityBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBarrel extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon innerTopIcon, topIcon, bottomIcon;
    
    public static int barrelModel = RenderingRegistry.getNextAvailableRenderId();
    private Random random = new Random();
    
    public BlockBarrel()
    {
        super(Material.wood);
        setResistance(2.5F);
        setHardness(2.5F);
        setHarvestLevel("axe", 0);
        setLightOpacity(500);
        setBlockName("barrel");
        setBlockTextureName("wellozmod:barrel");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.useNeighborBrightness = true;
    }
    
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityBarrel();
    }
    
    public boolean hasTileEntity()
    {
        return true;
    }
    
    public boolean hasComparatorInputOverride()
    {
        return true;
    }
    
    public int getComparatorInputOverride(World world, int x, int y, int z, int side)
    {
        return Container.calcRedstoneFromInventory(this.func_149951_m(world, x, y, z));
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
    {
        int l = BlockPistonBase.determineOrientation(world, x, y, z, entityLivingBase);
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.SOUND_TYPE_BARREL_OPEN[world.rand.nextInt(RegistrySounds.SOUND_TYPE_BARREL_OPEN.length)], 1.0F, 1.0F);
        }
        
        entityPlayer.openGui(WelloZMod.instance, RegistryUI.BARREL, world, x, y, z);
        return true;
    }
    
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        
        if ((tileEntity instanceof IInventory))
        {
            IInventory iinventory = (IInventory) tileEntity;
            
            for (int i1 = 0; i1 < iinventory.getSizeInventory(); i1++)
            {
                ItemStack itemStack = iinventory.getStackInSlot(i1);
                
                if (itemStack != null)
                {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    
                    EntityItem entityItem;
                    
                    for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemStack.stackSize > 0; world.spawnEntityInWorld(entityItem))
                    {
                        int j1 = this.random.nextInt(21) + 10;
                        
                        if (j1 > itemStack.stackSize)
                        {
                            j1 = itemStack.stackSize;
                        }
                        
                        itemStack.stackSize -= j1;
                        
                        entityItem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemStack.getItem(), j1, itemStack.getItemDamage()));
                        float f3 = 0.05F;
                        entityItem.motionX = (float) this.random.nextGaussian() * f3;
                        entityItem.motionY = (float) this.random.nextGaussian() * f3 + 0.2F;
                        entityItem.motionZ = (float) this.random.nextGaussian() * f3;
                        
                        if (itemStack.hasTagCompound())
                        {
                            entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                        }
                    }
                }
            }
            
            world.func_147453_f(x, y, z, block);
        }
        
        super.breakBlock(world, x, y, z, block, metadata);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        int k = BlockPistonBase.getPistonOrientation(metadata);
        return (k) > 5 ? metadata > 7 ? this.innerTopIcon : this.topIcon : (side == k ? (metadata > 7 ? this.innerTopIcon : this.topIcon) : (side == Facing.oppositeSide[k] ? this.bottomIcon : this.blockIcon));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:barrel_side");
        this.topIcon = iiconRegister.registerIcon("wellozmod:barrel_top");
        this.innerTopIcon = iiconRegister.registerIcon("wellozmod:barrel_top_open");
        this.bottomIcon = iiconRegister.registerIcon("wellozmod:barrel_bottom");
    }
    
    public int getRenderType()
    {
        return barrelModel;
    }
    
    private IInventory func_149951_m(World world, int x, int y, int z)
    {
        Object o = world.getTileEntity(x, y, z);
        
        if (o == null)
        {
            return null;
        }
        
        return (IInventory) o;
    }
}