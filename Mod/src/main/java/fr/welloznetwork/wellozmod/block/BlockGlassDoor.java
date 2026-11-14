package fr.welloznetwork.wellozmod.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGlassDoor extends BlockDoor
{
    @SideOnly(Side.CLIENT)
    private IIcon[] field_150017_a, field_150016_b;
    
    public BlockGlassDoor()
    {
        super(Material.glass);
        setHardness(3.0F);
        setBlockBounds(0.5F - 0.5F, 0.0F, 0.5F - 0.5F, 0.5F + 0.5F, 1.0F, 0.5F + 0.5F);
        setStepSound(soundTypeGlass);
        setBlockName("glass_door");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return this.field_150016_b[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.field_150017_a = new IIcon[2];
        this.field_150016_b = new IIcon[2];
        this.field_150017_a[0] = iiconRegister.registerIcon("wellozmod:glass_door_upper");
        this.field_150016_b[0] = iiconRegister.registerIcon("wellozmod:glass_door_lower");
        this.field_150017_a[1] = (IIcon) new IconFlipped(this.field_150017_a[0], true, false);
        this.field_150016_b[1] = (IIcon) new IconFlipped(this.field_150016_b[0], true, false);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side)
    {
        if (side != 1 && side != 0)
        {
            int x1 = this.func_150012_g(iBlockAccess, x, y, z);
            int y1 = x1 & 0x3;
            boolean flag = ((x1 & 0x4) != 0), flag1 = false, flag2 = ((x1 & 0x8) != 0);
            
            if (flag)
            {
                if (y1 == 0 && side == 2)
                {
                    flag1 = !flag1;
                }
                
                else if (y1 == 1 && side == 5)
                {
                    flag1 = !flag1;
                }
                
                else if (y1 == 2 && side == 3)
                {
                    flag1 = !flag1;
                }
                
                else if (y1 == 3 && side == 4)
                {
                    flag1 = !flag1;
                }
            }
            
            else
            {
                if (y1 == 0 && side == 5)
                {
                    flag1 = !flag1;
                }
                
                else if (y1 == 1 && side == 3)
                {
                    flag1 = !flag1;
                }
                
                else if (y1 == 2 && side == 4)
                {
                    flag = !flag1;
                }
                
                else if (y1 == 3 && side  == 2)
                {
                    flag1 = !flag1;
                }
                
                if ((y1 & 0x10) != 0)
                {
                    flag = !flag1;
                }
            }
            
            return flag2 ? this.field_150017_a[flag1 ? 1 : 0] : this.field_150016_b[flag1 ? 1 : 0];
        }
        
        return this.field_150016_b[0];
    }
    
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return ((metadata & 0x8) != 0) ? null : RegistryItems.item_glass_door;
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return RegistryItems.item_glass_door;
    }
}