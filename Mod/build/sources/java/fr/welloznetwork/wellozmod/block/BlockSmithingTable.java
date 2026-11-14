package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryUI;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSmithingTable extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon top, side, bottom;
    
    public BlockSmithingTable()
    {
        super(Material.rock);
        setResistance(2.5F);
        setHardness(2.5F);
        setHarvestLevel("axe", 0);
        setBlockName("smithing_table");
        setBlockTextureName("wellozmod:smithing_table_front");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            entityPlayer.openGui(WelloZMod.instance, RegistryUI.SMITHING_TABLE, world, x, y, z);
        }
        
        entityPlayer.triggerAchievement(RegistryAchievements.WHAT_TO_DO_WITH_THIS);
        
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1) 
        {
            return this.top;    
        } 
        
        else if (side == 3 || side == 2) 
        {
            return this.blockIcon;    
        }
        
        else if (side == 4 || side == 5) 
        {
            return this.side;
        }
        
        else if (side == 0)
        {
            return this.bottom;
        }
        
        return this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon("wellozmod:" + "smithing_table_front");
        this.top = iiconRegister.registerIcon("wellozmod:" + "smithing_table_top");
        this.side = iiconRegister.registerIcon("wellozmod:" + "smithing_table_side");
        this.bottom = iiconRegister.registerIcon("wellozmod:" + "smithing_table_bottom");
    }
}