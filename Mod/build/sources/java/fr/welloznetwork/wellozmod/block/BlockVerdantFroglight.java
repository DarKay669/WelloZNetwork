package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockVerdantFroglight extends Block
{
    private IIcon top, side;
    
    public BlockVerdantFroglight()
    {
        super(Material.rock);
        setHardness(0.3F);
        setLightLevel(15.0F);
        setBlockName("verdant_froglight");
        setBlockTextureName("wellozmod:verdant_froglight_side");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_FROGLIGHT);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 0)
        {
            return this.blockIcon;
        } else if (side == 1) {
            return this.top;
        } else if (side == 2 || side == 3 || side == 4 || side == 5) {
            return this.side;
        }
        
        return this.blockIcon;
    }
    
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "verdant_froglight_side");
        this.top = iiconRegister.registerIcon("wellozmod:" + "verdant_froglight_top");
        this.side = iiconRegister.registerIcon("wellozmod:" + "verdant_froglight_side");
    }
}