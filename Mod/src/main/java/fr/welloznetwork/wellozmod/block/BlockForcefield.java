package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;

public class BlockForcefield extends Block
{
    public BlockForcefield()
    {
        super(Material.rock);
        setLightOpacity(0);
        setBlockUnbreakable();
        setBlockName("forcefield");
        setBlockTextureName("wellozmod:forcefield");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(soundTypeGlass);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int i)
    {
        return iBlockAccess.getBlock(x, y, z) == this ? false : super.shouldSideBeRendered(iBlockAccess, x, y, z, i);
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean isCollidable()
    {
        return true;
    }
    
    public boolean canDropFromExplosion(Explosion explosion)
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    protected boolean canSilkHarvest()
    {
        return false;
    }
}