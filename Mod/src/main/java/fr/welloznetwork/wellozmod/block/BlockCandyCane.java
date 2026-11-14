package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCandyCane extends BlockLog
{
    public Item drop;
    private IIcon top;
    
    public BlockCandyCane()
    {
        setHardness(0.3F);
        setStepSound(soundTypeGlass);
        setBlockName("candy_cane_block");
        setBlockTextureName("wellozmod:candy_cane_side");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public int onBlockPlaced(World world, int x, int y, int z, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
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
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "candy_cane_side");
        this.top = iiconRegister.registerIcon("wellozmod:" + "candy_cane_top");
    }
    
    public void onBlockHarvested(World world, int x, int y, int z, int i1, EntityPlayer entityPlayer)
    {
        if (entityPlayer.getCurrentEquippedItem() != null)
        {
            if (entityPlayer.getCurrentEquippedItem().getItem() == RegistryItems.candy_cane_axe)
            {
                this.drop = Item.getItemFromBlock(RegistryBlocks.candy_cane_block);
            }
            
            else
            {
                this.drop = RegistryItems.candy_cane;
            }
        }
        
        else
        {
            this.drop = RegistryItems.candy_cane;
        }
    }
    
    public int quantityDropped(Random random)
    {
        if (this.drop != Item.getItemFromBlock(RegistryBlocks.candy_cane_block))
        {
            return random.nextInt(2) + 1;
        }
        
        return 1;
    }
    
    public Item getItemDropped(int i, Random random, int i2)
    {
        return this.drop;
    }
}