package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockGlazedTerracotta extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon blockIconFlipped;
    
    public static int terracottaModel = RenderingRegistry.getNextAvailableRenderId();
    private int metadata;
    
    public BlockGlazedTerracotta(String unlocalizedName)
    {
        super(Material.rock);
        setResistance(4.2F);
        setHardness(1.25F);
        setHarvestLevel("pickaxe", 0);
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(soundTypeStone);
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
    {
        int k = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, k, 2);
    }
    
    public MapColor getMapColor(int color)
    {
        return MapColor.getMapColorForBlockColored(color);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        switch (side)
        {
            case 0:
                return this.blockIconFlipped;
                
            case 2:
                return this.metadata % 2 == 0 ? this.blockIconFlipped : this.blockIcon;
                
            case 5:
                return this.metadata % 2 == 1 ? this.blockIconFlipped : this.blockIcon;
        }
        
        return this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        super.registerBlockIcons(iiconRegister);
        this.blockIconFlipped = new IconFlipped(this.blockIcon, true, false);
    }
    
    public int getRenderType()
    {
        return terracottaModel;
    }
}