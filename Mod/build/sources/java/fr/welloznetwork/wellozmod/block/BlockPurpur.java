package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.IBlockAccess;

public class BlockPurpur extends Block
{
	public BlockPurpur()
	{
		super(Material.rock);
		setHardness(1.5F);
		setResistance(6.0F);
		setHarvestLevel("pickaxe", 0);
		setStepSound(soundTypePiston);
		setBlockTextureName("wellozmod:purpur_block");
		setBlockName("purpur_block");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return !(entity instanceof EntityDragon);
	}
}