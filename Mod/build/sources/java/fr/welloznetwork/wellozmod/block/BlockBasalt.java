package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockBasalt extends Block
{
    private IIcon top;
    
    public BlockBasalt()
    {
        super(Material.rock);
        setHarvestLevel("pickaxe", 0);
        setResistance(4.2F);
        setHardness(1.25F);
        setBlockName("basalt");
        setBlockTextureName("wellozmod:basalt_side");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_BASALT);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1)
        {
            return this.top;            
        }
        
        else if (side == 2 || side == 3 || side == 4 || side == 5)
        {
            return this.blockIcon;
        }
        
        return this.blockIcon;
    }
    
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "basalt_side");
        this.top = iiconRegister.registerIcon("wellozmod:" + "basalt_top");
    }
}