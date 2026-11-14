package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMangroveLog extends BlockLog
{
    private IIcon top;
    
    public BlockMangroveLog()
    {
        setHarvestLevel("axe", 0);
        setResistance(2.0F);
        setHardness(2.0F);
        setBlockName("mangrove_log");
        setBlockTextureName("wellozmod:mangrove_log_side");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
    {
        int j1 = p_149660_9_ & 3;
        byte b0 = 0;
        
        switch (p_149660_5_)
        {
            case 0:
            case 1:
                b0 = 0;
                break;
            case 2:
            case 3:
                b0 = 8;
                break;
            case 4:
            case 5:
                b0 = 4;
        }
        
        return j1 | b0;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        int k = metadata & 12;
        int l = metadata & 3;
        
        return k == 0 && (side == 1 || side == 0) ? this.getTopIcon(l) : (k == 4 && (side == 5 || side == 4) ? this.getTopIcon(l) : (k == 8 && (side == 2 || side == 3) ? this.getTopIcon(l) : this.getSideIcon(l)));
    }
    
    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int side)
    {
        return this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int side)
    {
        return this.top;
    }
    
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "mangrove_log_side");
        this.top = iiconRegister.registerIcon("wellozmod:" + "mangrove_log_top");
    }
}