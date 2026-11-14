package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryAchievements;
import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemArmorIceSkates extends ItemArmor
{
    private int timer;
    
    public ItemArmorIceSkates()
    {
        super(RegistryArmorMaterials.ICE_SKATES, 0, 3);
        setUnlocalizedName("ice_skates");
        setTextureName("wellozmod:ice_skates");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
    {
        if (slot == 3)
        {
            return "wellozmod:textures/models/armors/ice_skates.png";
        }
        
        return "wellozmod:textures/models/armors/ice_skates.png";
    }
    
    public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack)
    {
        if (world.getBlock((int) entityPlayer.posX, (int) entityPlayer.posY - 1, (int) entityPlayer.posZ) == Blocks.ice || world.getBlock((int) entityPlayer.posX, (int) entityPlayer.posY - 1, (int) entityPlayer.posZ) == Blocks.packed_ice || world.getBlock((int) entityPlayer.posX, (int) entityPlayer.posY - 1, (int) entityPlayer.posZ) == RegistryBlocks.frosted_ice || world.getBlock((int) entityPlayer.posX, (int) entityPlayer.posY - 1, (int) entityPlayer.posZ) == RegistryBlocks.iced_cobblestone || world.getBlock((int) entityPlayer.posX, (int) entityPlayer.posY - 1, (int) entityPlayer.posZ) == RegistryBlocks.iced_stone)
        {
            entityPlayer.triggerAchievement(RegistryAchievements.IT_SLIPS);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 3));
            
            if (this.timer >= 100)
            {
                itemStack.damageItem(1, (EntityLivingBase) entityPlayer);
                this.timer = 0;
            }
            
            this.timer++;
        }
        
        else
        {
            entityPlayer.clearActivePotions();
        }
    }
}