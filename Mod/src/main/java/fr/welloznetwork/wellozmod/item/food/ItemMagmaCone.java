package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemMagmaCone extends ItemFood
{
    public ItemMagmaCone()
    {
        super(6, 0.0F, false);
        setUnlocalizedName("magma_cone");
        setTextureName("wellozmod:magma_cone");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 900, 2));
    }
}