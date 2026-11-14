package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockCrimsonNylium extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon topIcon, bottomIcon;
    
    public BlockCrimsonNylium()
    {
        super(Material.grass);
        setResistance(0.4F);
        setHardness(0.4F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("crimson_nylium");
        setBlockTextureName("wellozmod:crimson_nylium_side");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_NYLIUM);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1)
        {
            return this.topIcon;
        } 
        
        else if (side == 2 || side == 3 || side == 4 || side == 5) 
        {
            return this.blockIcon;
        }
        
        else if (side == 0)
        {
            return this.bottomIcon;
        }
        
        return this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:crimson_nylium_side");
        this.topIcon = iiconRegister.registerIcon("wellozmod:crimson_nylium");
        this.bottomIcon = iiconRegister.registerIcon("minecraft:dirt");
    }
}