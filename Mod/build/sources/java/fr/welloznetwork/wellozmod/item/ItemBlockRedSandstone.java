package fr.welloznetwork.wellozmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRedSandstone extends ItemBlock
{
	private final String[] types = new String[] {"default", "chiseled", "smooth"};
	
	public ItemBlockRedSandstone(Block block)
	{
		super(block);
		setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return getUnlocalizedName() + "_" + this.types[Math.min(Math.max(0, itemStack.getItemDamage()), this.types.length - 1)];
	}
	
	public int getMetadata(int metadata)
	{
		return metadata;
	}
}