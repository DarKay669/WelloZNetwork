package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockPurpurPillar extends BlockRotatedPillar
{
	public BlockPurpurPillar()
	{
		super(Material.rock);
		setHardness(1.5F);
		setResistance(6.0F);
		setHarvestLevel("pickaxe", 0);
		setStepSound(soundTypePiston);
		setBlockTextureName("wellozmod:purpur_pillar");
		setBlockName("purpur_pillar");
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
	}
	
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return !(entity instanceof EntityDragon);
	}
	
	@SideOnly(Side.CLIENT)
	protected IIcon getSideIcon(int side)
	{
		return this.blockIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iiconRegister)
	{
		super.registerBlockIcons(iiconRegister);
		this.field_150164_N = iiconRegister.registerIcon(this.getTextureName() + "_top");
	}
}