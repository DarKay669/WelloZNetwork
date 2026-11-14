package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class BlockBeetroot extends BlockCrops
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    
    public BlockBeetroot()
    {
        setBlockName("beetroots");
        setBlockTextureName("wellozmod:beetroots");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata < 7)
        {
            if (metadata == 6)
            {
                metadata = 5;
            }
            
            return this.icons[metadata >> 1];
        }
        
        else
        {
            return this.icons[3];
        }
    }
    
    protected Item func_149866_i()
    {
        return RegistryItems.beetroot_seeds;
    }
    
    protected Item func_149865_P()
    {
        return RegistryItems.beetroot;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.icons = new IIcon[4];
        
        for (int i = 0; i < this.icons.length; i++)
        {
            this.icons[i] = iiconRegister.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }
}