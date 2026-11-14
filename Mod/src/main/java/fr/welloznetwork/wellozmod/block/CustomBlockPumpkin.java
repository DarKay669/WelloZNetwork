package fr.welloznetwork.wellozmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@Custom(BlockPumpkin.class)
public class CustomBlockPumpkin extends BlockDirectional
{
    @SideOnly(Side.CLIENT)
    private IIcon topIcon, sideIcon;
    
    private String unlocalizedName;
    private boolean activate;
    
    public CustomBlockPumpkin(boolean activate, String unlocalizedName)
    {
        super(Material.gourd);
        setHardness(1.0F);
        setStepSound(soundTypeWood);
        setTickRandomly(true);
        setBlockName(unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_HALLOWEEN);
        this.activate = activate;
        this.unlocalizedName = unlocalizedName;
        
        if (activate)
        {
            setLightLevel(1.0F);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return side == 1 ? this.topIcon : (side == 0 ? this.topIcon : (metadata == 2 && side == 2 ? this.sideIcon : (metadata == 3 && side == 5 ? this.sideIcon : (metadata == 0 && side == 3 ? this.sideIcon : (metadata == 1 && side == 4 ? this.sideIcon : this.blockIcon)))));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister)
    {
         this.sideIcon = iiconRegister.registerIcon("wellozmod:" + this.unlocalizedName + (this.activate ? "on" : "_off"));
         this.topIcon = iiconRegister.registerIcon("minecraft:pumpkin_top");
         this.blockIcon = iiconRegister.registerIcon("minecraft:pumpkin_side");
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z);
    }
    
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
    {
        int l = MathHelper.floor_double((double) (entityLivingBase.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }
}