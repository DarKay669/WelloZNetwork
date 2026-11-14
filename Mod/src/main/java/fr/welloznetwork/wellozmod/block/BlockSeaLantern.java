package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

public class BlockSeaLantern extends Block
{
	public BlockSeaLantern()
	{
		super(Material.glass);
		setResistance(0.3F);
		setHardness(0.3F);
		setLightLevel(1.0F);
		setStepSound(soundTypeGlass);
		setBlockName("sea_lantern");
		setBlockTextureName("wellozmod:sea_lantern");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public boolean isOpaqueCube()
	{
	    return false;
	}
	
	public int quantityDropped(Random r)
	{
		return 2 + r.nextInt(2);
	}
	
	public int quantityDroppedWithBonus(int fortune, Random r)
	{
		return MathHelper.clamp_int(quantityDropped(r) + r.nextInt(fortune + 1), 1, 5);
	}
	
	public Item getItemDropped(int meta, Random r, int fortune)
	{
		return RegistryItems.prismarine_crystals;
	}
	
	public MapColor getMapColor(int meta)
	{
		return MapColor.quartzColor;
	}
	
	protected boolean canSilkHarvest()
	{
		return true;
	}
}