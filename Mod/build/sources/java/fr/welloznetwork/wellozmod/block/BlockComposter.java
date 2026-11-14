package fr.welloznetwork.wellozmod.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import fr.welloznetwork.wellozmod.item.crafting.ComposterRecipes;
import fr.welloznetwork.wellozmod.tileentity.IFakeTileEntity;
import fr.welloznetwork.wellozmod.tileentity.TileEntityFakeInventory;
import fr.welloznetwork.wellozmod.world.storage.loot.LootTableManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockComposter extends Block implements IFakeTileEntity
{
    @SideOnly(Side.CLIENT)
    public IIcon topIcon, bottomIcon, composterIcon, fullComposterIcon;
    
    private static final int[] SLOTS_NONE = new int[0], SLOTS_SINGLE = new int[] {0};
    public static int composterModel = RenderingRegistry.getNextAvailableRenderId();
        
    public BlockComposter()
    {
        super(Material.rock);
        setResistance(0.6F);
        setHardness(0.6F);
        setHarvestLevel("axe", 0);
        setLightOpacity(500);
        setBlockName("composter");
        setBlockTextureName("wellozmod:composter_side");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        useNeighborBrightness = true;
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        this.setBlockBoundsForItemRender();
    }
    
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        entityPlayer.triggerAchievement(RegistryAchievements.RECYCLING);
        
        int metadata = world.getBlockMetadata(x, y, z);
        
        if (metadata < 6)
        {
            ItemStack itemStack = entityPlayer.getCurrentEquippedItem();
            int chance = ComposterRecipes.getCompostChance(itemStack);
            
            if (chance > 0)
            {
                if (!world.isRemote)
                {
                    addToComposter(world, x, y, z, itemStack);
                    
                    if (!entityPlayer.capabilities.isCreativeMode)
                    {
                        itemStack.stackSize--;
                    }
                }
                
                for (int i = 0; i < 5; i++)
                {
                    world.spawnParticle("happyVillager", ((world.rand.nextDouble() * 0.875D) + 0.125D) + x, 0.25D + (0.125D * metadata) + y, ((world.rand.nextDouble() * 0.875D) + 0.125D) + z, 0.0D, 0.0D, 0.0D);
                }
                
                return true;
            }
        }
        
        else if (metadata == 7)
        {
            if (!world.isRemote)
            {
                EntityItem entityItem = new EntityItem(world, x + 0.5D, y + 1.0D, z + 0.5D, LootTableManager.COMPOSTER_LOOT.getOneItem(world.rand));
                entityItem.motionX = world.rand.nextDouble() * 0.5D;
                entityItem.motionY = 0.0D;
                entityItem.motionZ = world.rand.nextDouble() * 0.5D;
                world.spawnEntityInWorld(entityItem);
            }
            
            world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, RegistrySounds.soundTypeComposterEmpty[world.rand.nextInt(RegistrySounds.soundTypeComposterEmpty.length)], 1.0F, 1.0F, true);
            world.setBlockMetadataWithNotify(x, y, z, 0, 3);
            return true;
        }
        
        return false;
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.getBlockMetadata(x, y, z) == 6)
        {
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, RegistrySounds.soundTypeComposterReady[random.nextInt(RegistrySounds.soundTypeComposterReady.length)], 1.0F, 1.0F);
            world.setBlockMetadataWithNotify(x, y, z, 7, 3);
        }
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return composterModel;
    }
    
    public boolean isSideSolid(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection)
    {
        return forgeDirection != ForgeDirection.UP;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return side == 1 ? this.topIcon : side == 0 ? this.bottomIcon : this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:composter_side");
        this.bottomIcon = iiconRegister.registerIcon("wellozmod:composter_bottom");
        this.topIcon = iiconRegister.registerIcon("wellozmod:composter_top");
        this.composterIcon = iiconRegister.registerIcon("wellozmod:composter_compost");
        this.fullComposterIcon = iiconRegister.registerIcon("wellozmod:composter_ready");
    }
    
    private static boolean addToComposter(World world, int x, int y, int z, ItemStack itemStack)
    {
        int chance = ComposterRecipes.getCompostChance(itemStack);
        int metadata = world.getBlockMetadata(x, y, z);
        
        if (chance > 0)
        {
            int fillAmount = (int) (chance * 0.1F) + (world.rand.nextInt(100) < chance % 100 ? 1 : 0);
            
            if (fillAmount > 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, Math.min(metadata + fillAmount, 6), 3);
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, RegistrySounds.soundTypeComposterFillSuccess[world.rand.nextInt(RegistrySounds.soundTypeComposterFillSuccess.length)], 1.0F, 1.0F);
                
                if (fillAmount + metadata > 5)
                {
                    world.scheduleBlockUpdate(x, y, z, RegistryBlocks.composter, world.rand.nextInt(10) + 10);
                }
            }
            
            else
            {
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, RegistrySounds.soundTypeComposterFill[world.rand.nextInt(RegistrySounds.soundTypeComposterFill.length)], 1.0F, 1.0F);
            }
            
            return true;
        }
        
        return false;
    }
    
    public TileEntity getTileEntity(World world, int x, int y, int z)
    {
        ISidedInventory iSidedInventory;
        
        if (world.getBlockMetadata(x, y, z) < 6)
        {
            iSidedInventory = new InventoryEmptyComposter(world, x, y, z);
        }
        
        else if (world.getBlockMetadata(x, y, z) == 7)
        {
            iSidedInventory = new InventoryFullComposter(world, x, y, z);
        }
        
        else
        {
            iSidedInventory = new InventoryDummy(world, x, y, z);
        }
        
        return new TileEntityFakeInventory(iSidedInventory);
    }
    
    static class InventoryDummy extends InventoryBasic implements ISidedInventory
    {
        protected final int x, y, z;
        protected final World world;
        protected boolean dirty;
        
        public InventoryDummy(World world, int x, int y, int z, String name, int slots)
        {
            super(name, false, slots);
            this.x = x;
            this.y = y;
            this.z = z;
            this.world = world;
        }
        
        public InventoryDummy(World world, int x, int y, int z)
        {
            this(world, x, y, z, "dummy", 0);
        }
        
        public int[] getAccessibleSlotsFromSide(int side)
        {
            return SLOTS_NONE;
        }
        
        public boolean canInsertItem(int index, ItemStack itemStack, int side)
        {
            return false;
        }
        
        public boolean canExtractItem(int index, ItemStack itemStack, int side)
        {
            return false;
        }
        
        public int getInventoryStackLimit()
        {
            return 1;
        }
        
        public void markDirty()
        {
            this.dirty = true;
        }
    }
    
    static class InventoryEmptyComposter extends InventoryDummy
    {
        public InventoryEmptyComposter(World world, int x, int y, int z)
        {
            super(world, x, y, z, "composter", 1);
        }
        
        public int[] getAccessibleSlotsFromSide(int side)
        {
            return side == 1 ? SLOTS_SINGLE : SLOTS_NONE;
        }
        
        public boolean canInsertItem(int slot, ItemStack itemStack, int side)
        {
            return !this.dirty && side == 1 && addToComposter(this.world, this.x, this.y, this.z, itemStack);
        }
        
        public void setInventorySlotContents(int index, ItemStack itemStack)
        {
            this.markDirty();
        }
    }
    
    static class InventoryFullComposter extends InventoryDummy
    {
        private Random random = new Random();
        
        public InventoryFullComposter(World world, int x, int y, int z)
        {
            super(world, x, y, z, "composter_full", 1);
        }
        
        public int[] getAccessibleSlotsFromSide(int side)
        {
            return side == 0 ? SLOTS_SINGLE : SLOTS_NONE;
        }
        
        public boolean canExtractItem(int index, ItemStack itemStack, int side)
        {
            if (side == 0)
            {
                this.world.playSound(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D, RegistrySounds.soundTypeComposterEmpty[this.random.nextInt(RegistrySounds.soundTypeComposterEmpty.length)], 1.0F, 1.0F, true);
                this.world.setBlockMetadataWithNotify(this.x, this.y, this.z, 0, 3);
                return true;
            }
            
            return false;
        }
    }
}