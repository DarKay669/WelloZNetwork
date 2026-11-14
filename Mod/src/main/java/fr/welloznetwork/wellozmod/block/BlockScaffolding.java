package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockScaffolding extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon top, bottom;
    
    public BlockScaffolding()
    {
        super(Material.wood);
        setResistance(0.0F);
        setHardness(0.0F);
        setBlockName("scaffolding");
        setBlockTextureName("wellozmod:scaffolding_side");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1)
        {
            return this.top;
        } 
        
        else if (side == 0) 
        {
            return this.bottom;
        } 
        
        else if (side == 2 || side == 3 || side == 4 || side == 5)
        {
            return this.blockIcon;
        }
        
        return this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "scaffolding_side");
        this.top = iiconRegister.registerIcon("wellozmod:" + "scaffolding_top");
        this.bottom = iiconRegister.registerIcon("wellozmod:" + "scaffolding_bottom");
    }
} 