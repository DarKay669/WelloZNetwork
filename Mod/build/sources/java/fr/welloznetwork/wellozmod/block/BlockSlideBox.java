package fr.welloznetwork.wellozmod.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSlideBox extends Block
{
    private IIcon top, side;
    
    public BlockSlideBox()
    {
        super(Material.wood);
        setBlockUnbreakable();
        setResistance(6000000.0F);
        setHardness(6000000.0F);
        setStepSound(soundTypePiston);
        disableStats();
        setBlockName("slide_box");
        setBlockTextureName("wellozmod:slide_box_side");
        setStepSound(soundTypeWood);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
    {
        AxisAlignedBB axisAlignedBB2 = this.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            ((EntityPlayer) entity).triggerAchievement(RegistryAchievements.LITERALLY_DEAD);
        }
        
        entity.attackEntityFrom(DamageSource.outOfWorld, 20.0F);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 1 || side == 0)
        {
            return this.top;
        }
        
        else if (side == 2 || side == 3 || side == 4 || side == 5)
        {
            return this.side;
        }
        
        return this.side;
    }
    
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.side = iiconRegister.registerIcon("wellozmod:slide_box_side");
        this.top = iiconRegister.registerIcon("wellozmod:slide_box_top");
    }
}