package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCookieCone extends ItemFood
{
    public ItemCookieCone()
    {
        super(6, 0.0F, false);
        setUnlocalizedName("cookie_cone");
        setTextureName("wellozmod:cookie_cone");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 900, 2));
    }
}