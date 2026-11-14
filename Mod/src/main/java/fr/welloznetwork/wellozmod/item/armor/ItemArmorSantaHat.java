package fr.welloznetwork.wellozmod.item.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.model.ModelSantaHat;
import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorSantaHat extends ItemArmor
{
    public ItemArmorSantaHat()
    {
        super(RegistryArmorMaterials.SANTA, 0, 0);
        setUnlocalizedName("santa_hat");
        setTextureName("wellozmod:santa_hat");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
    {
        if (itemStack.getItem() == RegistryItems.santa_hat)
        {
            return "wellozmod:textures/models/armors/santa_hat1.png";
        }
        
        return "wellozmod:textures/models/armors/santa_hat1.png";
    }
    
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLivingBase, ItemStack itemStack, int armorSlot)
    {
        ModelBiped modelBiped = null;
        
        if (itemStack != null && itemStack.getItem() instanceof ItemArmorSantaHat)
        {
            int type = ((ItemArmor) itemStack.getItem()).armorType;
            
            if (type == 1 || type == 3)
            {
                modelBiped = new ModelSantaHat(1.0F);
            }
            
            else
            {
                modelBiped = new ModelSantaHat(-1.0F);
            }
            
            if (modelBiped != null)
            {
                modelBiped.bipedHead.showModel = (armorSlot == 0);
                modelBiped.isSneak = entityLivingBase.isSneaking();
                modelBiped.isRiding = entityLivingBase.isRiding();
                modelBiped.isChild = entityLivingBase.isChild();
                modelBiped.heldItemRight = (entityLivingBase.getEquipmentInSlot(0) != null) ? 1 : 0;
                
                if (entityLivingBase instanceof EntityPlayer)
                {
                    modelBiped.aimedBow = (((EntityPlayer) entityLivingBase).getItemInUseDuration() > 2);
                }
            }
            
            return modelBiped;
        }
        
        return null;
    }
}