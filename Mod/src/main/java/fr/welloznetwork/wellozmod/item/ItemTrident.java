package fr.welloznetwork.wellozmod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemTrident extends Item
{
    public ItemTrident()
    {
        setUnlocalizedName("trident");
        setTextureName("wellozmod:trident");
        setMaxStackSize(1);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.SOUND_TYPE_TRIDENT_THROW, 1.0F, 1.0F);
        
        entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 500, 0));
        
        if (!world.isRemote)
        {
            world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.SOUND_TYPE_TRIDENT_THUNDER, 1.0F, 1.0F);
            world.createExplosion(entityPlayer, entityPlayer.posX + 6.0D, entityPlayer.posY, entityPlayer.posZ + 6.0D, 10.0F, true);
        }
        
        itemStack.stackSize--;
        
        return itemStack;
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.rare;
    }
}