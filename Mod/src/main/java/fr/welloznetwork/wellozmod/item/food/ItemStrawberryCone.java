package fr.welloznetwork.wellozmod.item.food;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemStrawberryCone extends ItemFood
{
    public ItemStrawberryCone()
    {
        super(6, 0.0F, false);
        setUnlocalizedName("strawberry_cone");
        setTextureName("wellozmod:strawberry_cone");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 900, 2));
    }
}