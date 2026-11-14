package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockMuddyMangroveRoots extends Block
{
    private IIcon top;
    
    public BlockMuddyMangroveRoots()
    {
        super(Material.leaves);
        setHarvestLevel("shovel", 0);
        setResistance(0.7F);
        setHardness(0.7F);
        setBlockName("muddy_mangrove_roots");
        setBlockTextureName("wellozmod:muddy_mangrove_roots_side");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_MUDDY_MANGROVE_ROOTS);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1 || side == 0)
        {
            return this.top;
        } else if (side == 2 || side == 3 || side == 4 || side == 5) {
            return this.blockIcon;
        }
        
        return this.blockIcon;
    }
    
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "muddy_mangrove_roots_side");
        this.top = iiconRegister.registerIcon("wellozmod:" + "muddy_mangrove_roots_top");
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
}