package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockConcretePowder extends BlockFalling
{
    private static final Material concretePowder = new Material(MapColor.sandColor);
    
	public BlockConcretePowder(String unlocalizedName)
	{
		super(concretePowder);
		setBlockName(unlocalizedName);
		setResistance(0.5F);
		setHardness(0.5F);
		setHarvestLevel("shovel", 0);
		setBlockTextureName("wellozmod:" + unlocalizedName);
		setStepSound(soundTypeStone);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
	    this.func_149805_n(world, x, y, z);
	}
	
	private void func_149805_n(World world, int x, int y, int z)
	{
	    if (world.getBlock(x, y, z) == this)
	    {
	        if (this.blockMaterial == concretePowder)
	        {
	            boolean flag = false;
	            
	            if (flag || world.getBlock(x, y, z - 1).getMaterial() == Material.water)
                {
                    flag = true;
                }

                if (flag || world.getBlock(x, y, z + 1).getMaterial() == Material.water)
                {
                    flag = true;
                }

                if (flag || world.getBlock(x - 1, y, z).getMaterial() == Material.water)
                {
                    flag = true;
                }

                if (flag || world.getBlock(x + 1, y, z).getMaterial() == Material.water)
                {
                    flag = true;
                }

                if (flag || world.getBlock(x, y + 1, z).getMaterial() == Material.water)
                {
                    flag = true;
                }
                
                if (flag || world.getBlock(x, y - 1, z).getMaterial() == Material.water)
                {
                    flag = true;
                }

                if (flag)
                {
                    int l = world.getBlockMetadata(x, y, z);

                    if (l == 0)
                    {
                        world.setBlock(x, y, z, this);
                    }
                }
	        }
	    }
	}
}