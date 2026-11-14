package fr.welloznetwork.wellozmod.item.armor;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorCatPaw extends ItemArmor
{
    public ItemArmorCatPaw()
    {
        super(RegistryArmorMaterials.CAT_PAW, 0, 3);
        setUnlocalizedName("cat_paw_boots");
        setTextureName("wellozmod:cat_paw_boots");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack)
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
    {                    
        tooltip.add(I18n.format("enchantment.protect.fall"));
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
    {
        if (slot == 3)
        {
            return "wellozmod:textures/models/armors/cat_paw.png";
        }
        
        return "wellozmod:textures/models/armors/cat_paw.png";
    }
    
    public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack)
    {                
        if (entityPlayer.posY >= 64.0D && !entityPlayer.onGround)
        {
            entityPlayer.triggerAchievement(RegistryAchievements.LIGHT_AS_A_FEATHER);
        }
        
        entityPlayer.fallDistance = 0.0F;
    }
}