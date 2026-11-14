package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorCreeperCostume extends ItemArmor
{
    public ItemArmorCreeperCostume(String unlocalizedName, int armorType)
    {
        super(RegistryArmorMaterials.COSTUME, 0, armorType);
        setUnlocalizedName(unlocalizedName);
        setTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_HALLOWEEN);
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String armorType)
    {
        if (itemStack.getItem().equals(RegistryItems.creeper_chestplate) || itemStack.getItem().equals(RegistryItems.creeper_leggings))
        {
            return "wellozmod:textures/models/armors/creeper_1.png";
        }
        
        else if (itemStack.getItem().equals(RegistryItems.creeper_boots))
        {
            return "wellozmod:textures/models/armors/creeper_2.png";
        }
        
        return null;
    }
    
    public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack)
    {
        if (world.isRemote)   
        {
            world.spawnParticle("smoke", entityPlayer.posX + (world.rand.nextDouble() - 0.5D) * entityPlayer.width, entityPlayer.posY + world.rand.nextDouble() * entityPlayer.height, entityPlayer.posZ + (world.rand.nextDouble() - 0.5D) * entityPlayer.width, 0.0D, 0.0D, 0.0D);
        }
    }
}