package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.item.ItemBlockRedSandstone;
import net.minecraft.block.BlockSandStone;
import net.minecraft.item.ItemBlock;

public class BlockRedSandstone extends BlockSandStone implements ISubBlocksBlock
{
	public BlockRedSandstone()
	{
	    setResistance(0.8F);
		setHardness(0.8F);
		setHarvestLevel("pickaxe", 0);
		setBlockTextureName("wellozmod:red_sandstone");
		setBlockName("red_sandstone");
		setStepSound(soundTypeSand);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return (Class) ItemBlockRedSandstone.class;
	}
}