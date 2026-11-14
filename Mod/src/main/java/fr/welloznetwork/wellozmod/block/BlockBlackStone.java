package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockBlackStone extends Block
{
    private IIcon top;
    
    public BlockBlackStone()
    {
        super(Material.rock);
        setHarvestLevel("pickaxe", 0);
        setResistance(6.0F);
        setHardness(1.5F);
        setBlockName("black_stone");
        setBlockTextureName("wellozmod:black_stone");
        setStepSound(soundTypeStone);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
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
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "black_stone");
        this.top = iiconRegister.registerIcon("wellozmod:" + "black_stone_top");
    }
}