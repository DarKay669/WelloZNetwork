package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFertilizedDirt extends BlockBase
{
    public static boolean FERTILIZED_DIRT_GROWTHINDICATOR = false;
    public static int FERTILIZED_DIRT_GROWTH = 3;
    boolean tilled;
    
    public BlockFertilizedDirt(boolean tilled)
    {
        super("fertilized_dirt" + (tilled ? "_tilled" : ""), Material.ground);
        
        this.tilled = tilled;
        setTickRandomly(true);
        setHardness(0.6F);
        setStepSound(soundTypeGravel);
        
        if (tilled)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
            setLightOpacity(255);
        }
        
        if (!tilled)
        {
            setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        }
        
        else
        {
            setCreativeTab(null);
        }
        
        setBlockTextureName("wellozmod:fertilized_dirt");
    }
    
    public boolean renderAsNormalBlock()
    {
        return !this.tilled;
    }
    
    public String getUnlocalizedName()
    {
        return "tile.fertilized_dirt";
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox((x + 0), (y + 0), (z + 0), (x + 1), (y + 1), (z + 1));
    }
    
    public Item getItemDropped(int chance, Random random, int fortune)
    {
        return Item.getItemFromBlock(RegistryBlocks.fertilized_dirt);
    }
    
    public boolean isOpaqueCube()
    {
        return !this.tilled;
    }
    
    public boolean isFertile(World world, int x, int y, int z)
    {
        return true;
    }
    
    public boolean canSustainPlant(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection forgeDirection, IPlantable iPlantable)
    {
        Block block = iPlantable.getPlant(iBlockAccess, x, y + 1, z);
        EnumPlantType enumPlantType = iPlantable.getPlantType(iBlockAccess, x, y + 1, z);
        
        switch (enumPlantType)
        {
            case Desert:
                return !this.tilled;
                
            case Nether:
                return false;
                
            case Crop:
                return this.tilled;
                
            case Cave:
                return !this.tilled;
                
            case Plains:
                return !this.tilled;
                
            case Water:
                return false;
                
            case Beach:
                return !this.tilled;
        }
        
        return false;
    }
    
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            Block toBoost = world.getBlock(x, y + 1, z);
            
            if (toBoost != null && toBoost != Blocks.air && toBoost instanceof IPlantable)
            {
                if (this.FERTILIZED_DIRT_GROWTHINDICATOR)
                {
                    world.playAuxSFX(2005, x, y + 1, z, 0);
                }
            }
            
            for (int i = 0; i < this.FERTILIZED_DIRT_GROWTH; i++)
            {
                toBoost = world.getBlock(x, y + 1, z);
                
                if (toBoost != null && toBoost != Blocks.air && toBoost instanceof IPlantable)
                {
                    toBoost.updateTick(world, x, y + 1, z, random);
                }
            }
        }
    }
}