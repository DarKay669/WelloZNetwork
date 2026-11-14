package fr.welloznetwork.wellozmod.block;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSweetBerryBush extends BlockBush implements IGrowable
{
    private static final DamageSource BERRY_BUSH = new DamageSource("berryBush");
    
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    
    public BlockSweetBerryBush()
    {
        super(Material.vine);
        setBlockName("sweet_berry_bush");
        setBlockTextureName("wellozmod:sweet_berry_bush");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setTickRandomly(true);
        setStepSound(RegistrySounds.SOUND_TYPE_SWEET_BERRY_BUSH);
    }
    
    public boolean isReplaceable(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        return false;
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.getBlockMetadata(x, y, z) >= 3)
        {
            return;
        }
        
        int i = world.getBlockMetadata(x, y, z);
        
        if (i < 3 && world.rand.nextInt(5) == 0 && world.getBlockLightValue(x, y, z) >= 9)
        {
            world.setBlockMetadataWithNotify(x, y, z, i + 1, 2);
        }
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            entity.motionX *= 0.3405D;
            entity.motionY *= 0.3405D;
            entity.motionZ *= 0.3405D;
            entity.fallDistance = 0.0F;
            
            if (!world.isRemote && world.getBlockMetadata(x, y, z) >= 0 && (entity.lastTickPosX != entity.posX || entity.lastTickPosZ != entity.posZ))
            {
                double d0 = Math.abs(entity.posX - entity.lastTickPosX);
                double d1 = Math.abs(entity.posZ - entity.lastTickPosZ);
                
                if (d0 >= 0.003F && d1 >= 0.003F)
                {
                    entity.attackEntityFrom(BERRY_BUSH, 1.0F);
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.icons = new IIcon[4];
        
        for (int i = 0; i < this.icons.length; i++)
        {
            this.icons[i] = iiconRegister.registerIcon(this.getTextureName() + "_stage" + i);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata > 3)
        {
            return this.icons[0];
        }
        
        return this.icons[metadata];
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return RegistryItems.sweet_berries;
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        if (iBlockAccess.getBlockMetadata(x, y, z) == 0)
        {
            this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.5F, 0.8125F);
        }
        
        else
        {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
        }
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        int i = world.getBlockMetadata(x, y, z);
        
        if (i > 1 && (i != 2 || entityPlayer.getHeldItem() == null || entityPlayer.getHeldItem().getItem() != Items.dye || entityPlayer.getHeldItem().getItemDamage() != 15))
        {
            if (!world.isRemote && !world.restoringBlockSnapshots)
            {
                ItemStack itemStack = new ItemStack(RegistryItems.sweet_berries, 1 + world.rand.nextInt(i));
                double d0 = world.rand.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
                double d1 = world.rand.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
                double d2 = world.rand.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
                
                EntityItem entityItem = new EntityItem(world, x + d0, y + d1, z + d2, itemStack);
                entityItem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityItem);
            }
            
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
            return true;
        }
        
        return false;
    }
    
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        
        int count = metadata - world.rand.nextInt(2);
        
        for (int i = 0; i < count && metadata > 1; i++)
        {
            Item item = this.getItemDropped(metadata, world.rand, fortune);
            
            if (item != null)
            {
                drops.add(new ItemStack(item, 1, this.damageDropped(metadata)));
            }
        }
        
        return drops;
    }
    
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return RegistryItems.sweet_berries;
    }
    
    public boolean isFlammable(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection)
    {
        return true;
    }
    
    public int getFlammability(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection)
    {
        return 100;
    }
    
    public int getFireSpreadSpeed(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection)
    {
        return 60;
    }
    
    public boolean func_149851_a(World world, int x, int y, int z, boolean b)
    {
        return world.getBlockMetadata(x, y, z) < 3;
    }
    
    public boolean func_149852_a(World world, Random random, int x, int y, int z)
    {
        return true;
    }
    
    public void func_149853_b(World world, Random random, int x, int y, int z)
    {
        int i = world.getBlockMetadata(x, y, z);
        
        if (i < 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 2);
        }
    }
}