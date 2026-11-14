package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLapisLamp extends Block
{
    public BlockLapisLamp()
    {
        super(Material.redstoneLight);
        setHardness(0.3F);
        setStepSound(soundTypeGlass);
        setBlockName("lapis_lamp");
        setBlockTextureName("wellozmod:lapis_lamp");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public int getRenderColor(int color)
    {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        return 16777215;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        world.updateLightByType(EnumSkyBlock.Block, x, y, z);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    public int getLightValue(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        Block block = iBlockAccess.getBlock(x, y, z);
        
        if (block != this)
        {
            return block.getLightValue(iBlockAccess, x, y, z);
        }
        
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            return 15;
        }
        
        return 0;
    }
}