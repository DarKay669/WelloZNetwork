package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryUI;
import fr.welloznetwork.wellozmod.tileentity.TileEntityOnlineDetector;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockOnlineDetector extends BlockContainer
{
    private IIcon[] icons;
    
    public BlockOnlineDetector()
    {
        super(Material.rock);
        setStepSound(soundTypeGlass);
        setHarvestLevel("pickaxe", 1);
        setResistance(4.0F);
        setHardness(4.0F);
        setBlockName("online_detector");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            entityPlayer.openGui(WelloZMod.instance, RegistryUI.ONLINE_DETECTOR, world, x, y, z);
            entityPlayer.triggerAchievement(RegistryAchievements.SOMEONE_REALLY_WATCHING_YOU);
        }
        
        return true;
    }
    
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityOnlineDetector();
    }
    
    public void registerBlockIcons(IIconRegister iIconRegister)
    {
        this.icons = new IIcon[2];
        this.icons[0] = iIconRegister.registerIcon("wellozmod:online_detector_off");
        this.icons[1] = iIconRegister.registerIcon("wellozmod:online_detector_on");
    }
    
    public IIcon getIcon(int side, int metadata)
    {
        return this.icons[metadata];
    }
}