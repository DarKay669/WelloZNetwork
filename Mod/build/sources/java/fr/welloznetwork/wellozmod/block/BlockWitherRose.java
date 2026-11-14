package fr.welloznetwork.wellozmod.block;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWitherRose extends BlockBush
{
    public BlockWitherRose()
    {
        super(Material.plants);
        setTickRandomly(true);
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("wither_rose");
        setBlockTextureName("wellozmod:wither_rose");
        setBlockBounds(0.5F - 0.2F, 0.0F, 0.5F - 0.2F, 0.5F + 0.2F, 0.2F * 3.0F, 0.5F + 0.2F);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return this.canPlace(world, x, y, z);
    }
    
    protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.soul_sand;
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        this.checkAndDropBlock(world, x, y, z);
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        this.checkAndDropBlock(world, x, y, z);
    }
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return this.canPlace(world, x, y, z);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        double posX = x + 0.5D + (random.nextDouble() - 0.5D) * 0.5D;
        double posY = y + 0.6D + (random.nextDouble() - 0.5D) * 0.5D;
        double posZ = z + 0.5D + (random.nextDouble() - 0.5D) * 0.5D;
        double velY = (random.nextDouble()) * 0.05D;
        
        world.spawnParticle("smoke", posX, posY, posZ, 0.0D, velY, 0.0D);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            
            if (!this.hasWitherEffect(entityLivingBase.getActivePotionEffects())) 
            {
                entityLivingBase.addPotionEffect(new PotionEffect(Potion.wither.id, 60, 0, true));
            }
        }
    }
    
    private boolean hasWitherEffect(Collection collection) 
    {
        Iterator iterator = collection.iterator();
        
        while (iterator.hasNext())
        {
            Object obj = iterator.next();
            
            if (obj instanceof PotionEffect)
            {
                PotionEffect potionEffect = (PotionEffect) obj;
                
                if (potionEffect.getPotionID() == Potion.wither.id)
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean canPlace(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y - 1, z);
        return block.isSideSolid(world, x, y - 1, z, ForgeDirection.UP) || block == Blocks.farmland || block == Blocks.flower_pot;
    }
}