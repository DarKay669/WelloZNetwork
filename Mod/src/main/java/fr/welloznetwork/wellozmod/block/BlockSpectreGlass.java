package fr.welloznetwork.wellozmod.block;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSpectreGlass extends Block
{
    public BlockSpectreGlass()
    {
        super(Material.ground);
        setHardness(0.3F);
        setStepSound(soundTypeGlass);
        setBlockName("spectre_glass");
        setBlockTextureName("wellozmod:spectre_glass");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    public int getRenderColor(int color)
    {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        return 16777215;
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List listAlignedBBs, Entity entity)
    {
        AxisAlignedBB axisAlignedBB1 = getCollisionBoundingBoxFromPool(world, x, y, z);
        
        if (axisAlignedBB1 != null && axisAlignedBB.intersectsWith(axisAlignedBB1) && entity instanceof EntityPlayer)
        {
            listAlignedBBs.add(axisAlignedBB1);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side)
    {
        Block block = iBlockAccess.getBlock(x, y, z);
        ForgeDirection forgeDirection = ForgeDirection.VALID_DIRECTIONS[side];
        
        if  (block == this)
        {
            return false;
        }
        
        if (iBlockAccess.isAirBlock(x, y, z) || !block.isOpaqueCube())
        {
            return true;
        }
        
        return false;
    }
}