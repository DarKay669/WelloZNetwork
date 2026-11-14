package fr.welloznetwork.wellozmod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShield extends Item
{    
    public ItemShield()
    {
        setUnlocalizedName("shield");
        setTextureName("wellozmod:shield");
        setMaxStackSize(1);
        setMaxDamage(336);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return super.onItemUse(itemStack, entityPlayer, world, x, y, z, side, hitX, hitY, hitZ);
    }
    
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.block;
    }
    
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
        return 72000;
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_SHIELD_BLOCK[world.rand.nextInt(RegistrySounds.ITEM_SHIELD_BLOCK.length)], 1.0F, 1.0F);
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        
        return itemStack;
    }
    
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Item.getItemFromBlock(Blocks.planks) || repair.getItem() == Item.getItemFromBlock(RegistryBlocks.cherry_planks) || repair.getItem() == Item.getItemFromBlock(RegistryBlocks.mangrove_planks) ? true : super.getIsRepairable(toRepair, repair);
    }
}