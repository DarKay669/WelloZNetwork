package fr.welloznetwork.wellozmod.block.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockPoisonWater extends BlockFluidClassic 
{
    @SideOnly(Side.CLIENT)
    private IIcon stillIcon, flowingIcon;
    
    public BlockPoisonWater(Fluid fluid, Material material)
    {
        super(fluid, material);
        this.quantaPerBlock = 4;
        setLightOpacity(3);
        setBlockName("poison_water");
        disableStats();
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
        
        if (entity instanceof EntityLivingBase)
        {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 100));
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 100));
        }
        
        if (entity instanceof EntityPlayer)
        {
            ((EntityPlayer) entity).triggerAchievement(RegistryAchievements.BAD_SWIM);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return (side == 0 || side == 1) ? this.stillIcon : this.flowingIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.stillIcon = iiconRegister.registerIcon("wellozmod:poison_water_still");
        this.flowingIcon = iiconRegister.registerIcon("wellozmod:poison_water_flowing");
    }
    
    public boolean displaceIfPossible(World world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).getMaterial().isLiquid())
        {
            return false;
        }
        
        return super.displaceIfPossible(world, x, y, z);
    }
    
    public boolean canDisplace(IBlockAccess iBlockAccess, int x, int y, int z)
    {
        if (iBlockAccess.getBlock(x, y, z).getMaterial().isLiquid())
        {
            return false;
        }
        
        return super.canDisplace(iBlockAccess, x, y, z);
    }
}