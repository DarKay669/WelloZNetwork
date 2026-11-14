package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockConcrete extends Block
{
	public BlockConcrete(String unlocalizedName)
	{
		super(Material.rock);
		setBlockName(unlocalizedName);
		setResistance(1.8F);
		setHardness(1.8F);
		setHarvestLevel("pickaxe", 0);
		setBlockTextureName("wellozmod:" + unlocalizedName);
		setStepSound(soundTypeStone);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
}