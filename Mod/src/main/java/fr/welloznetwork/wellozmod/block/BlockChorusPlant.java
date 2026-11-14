package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChorusPlant extends Block
{   
    public static int chorusPlantModel = -1;
    
	public BlockChorusPlant()
	{
		super(Material.wood);
		setStepSound(soundTypeWood);
		setBlockName("chorus_plant");
		setBlockTextureName("wellozmod:chorus_plant");
		setHardness(0.5F);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
		
		this.chorusPlantModel = RenderingRegistry.getNextAvailableRenderId();
	}
	
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
	    return !(entity instanceof EntityDragon);
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbour)
	{
	    if (neighbour == this)
	    {
	        world.func_147480_a(x, y, z, true);
	    }
	}
	
	public boolean renderAsNormalBlock()
	{
	    return false;
	}
	
	public boolean isOpaqueCube()
	{
	    return false;
	}
	
	protected boolean canSilkHarvest()
	{
	    return false;
	}
	
	public int getRenderType()
	{
	    return this.chorusPlantModel;
	}
	
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		return RegistryItems.chorus_fruit;
	}
}