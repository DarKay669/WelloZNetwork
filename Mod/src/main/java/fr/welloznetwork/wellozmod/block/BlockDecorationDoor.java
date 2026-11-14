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

public class BlockDecorationDoor extends BlockDoor
{
    public IIcon topDoorIcon;
    public IIcon[] flippedIcons = new IIcon[2];
    
    @SideOnly(Side.CLIENT)
    private IIcon[] field_150017_a, field_150016_b;
    
    public BlockDecorationDoor()
    {
        super(Material.wood);
        setHardness(3.0F);
        setBlockBounds(0.5F - 0.5F, 0.0F, 0.5F - 0.5F, 0.5F + 0.5F, 1.0F, 0.5F + 0.5F);
        setStepSound(soundTypeWood);
        setBlockName("decoration_door");
    }
    
    public IIcon getIcon(int par1, int par2)
    {
        return this.field_150016_b[0];
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int i, int j, int k, int i2)
    {
        if (i2 != 1 && i2 != 0)
        {
            int i1 = func_150012_g(access, i, j, k);
            int j1 = i1 & 0x3;
            boolean flag = ((i1 & 0x4) != 0), flag1 = false, flag2 = ((i1 & 0x8) != 0);
            
            if (flag)
            {
                if (j1 == 0 && i2 == 2)
                {
                    flag1 = !flag1;
                }
                
                else if (j1 == 1 && i2 == 5)
                {
                    flag1 = !flag1;
                }
                
                else if (j1 == 2 && i2 == 3)
                {
                    flag1 = !flag1;
                }
                
                else if (j1 == 3 && i2 == 4)
                {
                    flag1 = !flag1;
                }
            } else {
                
                if (j1 == 0 && i2 == 5)
                {
                    flag1 = !flag1;

                }
                
                else if (j1 == 1 && i2 == 3)
                {
                    flag1 = !flag1;
                }
                
                else if (j1 == 2 && i2 == 4)
                {
                    flag = !flag1;
                }
                
                else if (j1 == 3 && i2 == 2)
                {
                    flag1 = !flag1;
                }
                
                if ((i1 & 0x10) != 0)
                {
                    flag1 = !flag1;
                }
            }
            
            return flag2 ? this.field_150017_a[flag1 ? 1 : 0] : this.field_150016_b[flag1 ? 1 : 0];
        }
        
        return this.field_150016_b[0];
    }
    
    public Item getItemDropped(int metadata, Random rand, int fortune)
    {
        return ((metadata & 0x8) != 0) ? null : RegistryItems.item_decoration_door;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.field_150017_a = new IIcon[2];
        this.field_150016_b = new IIcon[2];
        this.field_150017_a[0] = iiconRegister.registerIcon("wellozmod:decoration_door_upper");
        this.field_150016_b[0] = iiconRegister.registerIcon("wellozmod:decoration_door_lower");
        this.field_150017_a[1] = (IIcon) new IconFlipped(this.field_150017_a[0], true, false);
        this.field_150016_b[1] = (IIcon) new IconFlipped(this.field_150016_b[0], true, false);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return RegistryItems.item_decoration_door;
    }
}