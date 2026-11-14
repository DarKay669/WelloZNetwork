package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.IBlockAccess;

public class BlockEndBricks extends Block
{
	public BlockEndBricks()
	{
		super(Material.rock);
		setResistance(9.0F);
		setHardness(3.0F);
		setHarvestLevel("pickaxe", 0);
		setStepSound(soundTypeStone);
		setBlockTextureName("wellozmod:end_bricks");
		setBlockName("end_bricks");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return !(entity instanceof EntityDragon);
	}
}