package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPrismarineBricks extends Block
{
	public BlockPrismarineBricks() 
	{
		super(Material.rock);
		setResistance(6.0F);
		setBlockName("prismarine_bricks");
		setBlockTextureName("wellozmod:prismarine_bricks");
		setHardness(1.5F);
		setHarvestLevel("pickaxe", 0);
		setStepSound(soundTypeStone);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
}