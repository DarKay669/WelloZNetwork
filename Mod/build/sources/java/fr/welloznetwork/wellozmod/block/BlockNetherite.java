package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockNetherite extends Block
{   
	public BlockNetherite()
	{
		super(Material.rock);
		setResistance(1200.0F);
		setHardness(50.0F);
		setHarvestLevel("pickaxe", 3);
		setBlockName("netherite_block");
		setBlockTextureName("wellozmod:netherite_block");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		setStepSound(RegistrySounds.SOUND_TYPE_NETHERITE);
	}
	
	public boolean isBeaconBase(IBlockAccess iBlockAccess, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
	{
	    return true;
	}
}