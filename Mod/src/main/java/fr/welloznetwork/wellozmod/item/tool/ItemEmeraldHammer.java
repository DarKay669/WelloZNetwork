package fr.welloznetwork.wellozmod.item.tool;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryToolMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemEmeraldHammer extends ItemPickaxe
{    
    public ItemEmeraldHammer()
    {
        super(RegistryToolMaterials.EMERALD);
        setUnlocalizedName("emerald_hammer");
        setTextureName("wellozmod:emerald_hammer");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean onBlockStartBreak(ItemStack itemStack, int x, int y, int z, EntityPlayer entityPlayer)
    {
        if (!entityPlayer.worldObj.isRemote)
        {
            MovingObjectPosition movingObjectPosition = this.raytraceFromEntity(entityPlayer.worldObj, entityPlayer, true, 10.0D);
            
            if (movingObjectPosition != null)
            {
                breakOtherBlock(entityPlayer, itemStack, x, y, z, x, y, z, movingObjectPosition.sideHit);
                itemStack.damageItem(1, entityPlayer);
            }
        }
        
        return false;
    }
    
    public int getItemEnchantability()
    {
        return 0;
    }
    
    public boolean isBookEnchantable(ItemStack itemStack, ItemStack book)
    {
        return false;
    }
    
    public float getDigSpeed(ItemStack itemStack, Block block, int metadata)
    {
        return super.getDigSpeed(itemStack, block, metadata) / 9.0F;
    }
    
    public void breakOtherBlock(EntityPlayer entityPlayer, ItemStack itemStack, int x, int y, int z, int originX, int originY, int originZ, int side)
    {
        World world = entityPlayer.worldObj;
        
        if (world.isAirBlock(x, y, z))
        {
            return;
        }
        
        ForgeDirection forgeDirection = ForgeDirection.getOrientation(side);
        
        int range = Math.max(0, 1), rangeY = Math.max(1, range);
        
        boolean doX = (forgeDirection.offsetX == 0), doY = (forgeDirection.offsetY == 0), doZ = (forgeDirection.offsetZ == 0);
        
        this.removeBlocksInIteration(entityPlayer, itemStack, world, x, y, z, doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0, doX ? (range + 1) : 1, doY ? (rangeY * 2) : 1, doZ ? (range + 1) : 1, null, new Material[] {Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil, Material.snow, Material.craftedSnow, Material.clay}, false, 0, true, false);
    }
    
    public void removeBlocksInIteration(EntityPlayer entityPlayer, ItemStack itemStack, World world, int x, int y, int z, int xs, int ys, int zs, int xe, int ye, int ze, Block block, Material[] materialsListing, boolean smelt, int fortune, boolean dispose, boolean obsidian)
    {
        float blockHardness = block == null ? 1.0F : block.getBlockHardness(world, x, y, z);
        
        for (int x1 = xs; x1 < xe; x1++)
        {
            for (int y1 = ys; y1 < ye; y1++)
            {
                for (int z1 = zs; z1 < ze; z1++)
                {
                    if ((world.getBlock(x1 + x, y1 + y, z1 + z).isToolEffective("pickaxe", 3)) || ((world.getBlock(x1 + x, y1 + y, z1 + z) instanceof BlockRedstoneOre)) || ((obsidian) && ((world.getBlock(x1 + x, y1 + y, z1 + z) instanceof BlockObsidian))))
                    {
                        this.removeBlockWithDrops(entityPlayer, itemStack, world, x1 + x, y1 + y, z1 + z, x, y, z, world.getBlock(x1 + x, y1 + y, z1 + z), materialsListing, smelt, fortune, blockHardness, dispose, obsidian);
                    }
                }
            }
        }
    }
    
    private void removeBlockWithDrops(EntityPlayer entityPlayer, ItemStack itemStack, World world, int x, int y, int z, int bx, int by, int bz, Block block, Material[] materialsListing, boolean smelt, int fortune, float blockHardness, boolean dispose, boolean obsidian)
    {
        this.removeBlockWithDrops(entityPlayer, itemStack, world, x, y, z, bx, by, bz, block, materialsListing, smelt, fortune, blockHardness, dispose, true, obsidian);
    }
    
    private void removeBlockWithDrops(EntityPlayer entityPlayer, ItemStack itemStack, World world, int x, int y, int z, int bx, int by, int bz, Block block, Material[] materialsListing, boolean smelt, int fortune, float blockHardness, boolean dispose, boolean particles, boolean obsidian)
    {
        if (!world.blockExists(x, y, z)) 
        {
            return;
        }
        
        Block blk = world.getBlock(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        
        if ((block != null) && (blk != block))
        {
            return;
        }
        
        Material material = world.getBlock(x, y, z).getMaterial();
        boolean flag = false;
        
        if ((!world.isRemote) && (blk != null) && (!blk.isAir(world, x, y, z)) && (blk.getPlayerRelativeBlockHardness(entityPlayer, world, x, y, z) > 0.0F))
        {
            if ((!blk.canHarvestBlock(entityPlayer, metadata)) || (!this.isRightMaterial(blk, materialsListing)) || ((blk == Blocks.obsidian) && (!obsidian)))
            {
                return;
            }
            
            if (!entityPlayer.capabilities.isCreativeMode)
            {
                int localMetadata = world.getBlockMetadata(x, y, z);
                blk.onBlockHarvested(world, x, y, z, localMetadata, entityPlayer);
                
                if (blk.removedByPlayer(world, entityPlayer, x, y, z, true)) 
                {
                    blk.onBlockDestroyedByPlayer(world, x, y, z, localMetadata);
                }
                
                if (smelt)
                {
                    ItemStack result = FurnaceRecipes.smelting().getSmeltingResult((ItemStack) blk.getDrops(world, x, y, z, blk.getDamageValue(world, x, y, z), fortune).get(0));
                    
                    if (result == null)
                    {
                        blk.dropBlockAsItem(world, x, y, z, localMetadata, fortune);
                    }
                    
                    else
                    {
                        flag = true;
                        ItemStack resultCopy = result.copy();
                        float count = 1.0F;
                        
                        if ((fortune > 0) && (this.isMineral(block)))
                        {
                            count = 1 + (fortune - world.rand.nextInt(fortune)) / 2;
                        }
                        
                        if (count <= 0.0F)
                        {
                            count = 1.0F;
                        }
                        
                        resultCopy.stackSize = ((int) count);
                        EntityItem entityItem = new EntityItem(world, x, y, z, resultCopy);
                        world.spawnEntityInWorld(entityItem);
                    }
                } 
                
                else 
                {
                    blk.dropBlockAsItem(world, x, y, z, localMetadata, fortune);
                    
                }
            }
            
            else 
            {
                int localMeta = world.getBlockMetadata(x, y, z);
                blk.dropBlockAsItem(world, x, y, z, localMeta, fortune);
            }
            
            int xpDrop = blk.getExpDrop(world, blk.getDamageValue(world, x, y, z), fortune);
            EntityXPOrb entityXPOrb = new EntityXPOrb(world, x, y, z, xpDrop);
            
            if (xpDrop > 0)
            {
                world.spawnEntityInWorld(entityXPOrb);
            }
        } 
        
        else 
        {
            world.setBlockToAir(x, y, z);
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(blk) + (metadata << 12));
            world.setBlockToAir(x, y, z);
            
            if (flag)
            {
                world.spawnParticle("flame", x + 0.5D + 0.5D * world.rand.nextDouble(), y - 0.1D, z + 0.5D * world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", x + 0.5D + 0.5D * world.rand.nextDouble(), y - 0.1D, z + 0.5D * world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", x + 0.5D + 0.5D * world.rand.nextDouble(), y - 0.1D, z + 0.5D * world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    private boolean isMineral(Block block)
    {
        for (Block b : new Block[] {Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.lit_redstone_ore, RegistryBlocks.random_ore, RegistryBlocks.ruby_ore, RegistryBlocks.xp_ore, RegistryBlocks.zarkonium_ore, RegistryBlocks.zarux_ore, RegistryBlocks.deepslate_coal_ore, RegistryBlocks.deepslate_copper_ore, RegistryBlocks.deepslate_diamond_ore, RegistryBlocks.deepslate_emerald_ore, RegistryBlocks.deepslate_gold_ore, RegistryBlocks.deepslate_iron_ore, RegistryBlocks.deepslate_lapis_ore, RegistryBlocks.deepslate_redstone_ore})
        {
            if (b == block)
            {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isRightMaterial(Block block, Material[] material)
    {
        Material material1 = block.getMaterial();
        
        for (Material material2 : material)
        {
            if (material1 == material2)
            {
                return true;
            }
        }
        
        return false;
    }
    
    private MovingObjectPosition raytraceFromEntity(World world, Entity entity, boolean par3, double range)
    {
        float f = 1.0F;
        float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f;
        float f2 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f;
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * f;
        double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * f;
        
        if ((!world.isRemote) && ((entity instanceof EntityPlayer)))
        {
            d1 += ((EntityPlayer) entity).eyeHeight;
        }
        
        double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;
        Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
        return world.rayTraceBlocks(vec3, vec31, par3);
    }
}