package fr.welloznetwork.wellozmod.item.armor;

import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.mixins.entity.IMixinEntityLivingBaseAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemArmorObsidian extends ItemArmor
{
    public ItemArmorObsidian(String unlocalizedName, int type)
    {
        super(RegistryArmorMaterials.OBSIDIAN, 0, type);
        setUnlocalizedName(unlocalizedName);
        setTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_V2);
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
    {
        if (slot == 2)
        {
            return "wellozmod:textures/models/armors/obsidian_2.png";
        }
        
        return "wellozmod:textures/models/armors/obsidian_1.png";
    }
    
    public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack)
    {
        if (entityPlayer.isRiding())
        {
            entityPlayer.mountEntity(null);
        }
        
        entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 50, 0));
        
        switch (this.armorType)
        {
            case 0:
                break;
                
            case 1:
                break;
                
            case 2:
                
                if (entityPlayer.isInWater() || world.getBlock((int) entityPlayer.posX, (int) entityPlayer.boundingBox.minY, (int) entityPlayer.posZ).getMaterial() == Material.lava)
                {
                    if (world.getBlock((int) entityPlayer.posX, (int) (entityPlayer.posY + entityPlayer.getEyeHeight()), (int) entityPlayer.posZ).getMaterial() == Material.water)
                    {
                        if (!((IMixinEntityLivingBaseAccessor) entityPlayer).isJumping())
                        {
                            entityPlayer.motionY -= 0.05D;
                            
                            if (entityPlayer.motionY < -0.5D)
                            {
                                entityPlayer.motionY = -0.5D;
                            }
                        }
                    }
                }
                
                break;
                
            case 3:
                
                if (!entityPlayer.isSneaking() && world.getBlock((int) entityPlayer.posX, (int) entityPlayer.boundingBox.minY, (int) entityPlayer.posZ).getMaterial() == Material.lava)
                {
                    entityPlayer.motionY = 0.25D;
                    
                    if (entityPlayer.isCollidedHorizontally)
                    {
                        entityPlayer.onGround = true;
                    }
                }
        }
    }
}