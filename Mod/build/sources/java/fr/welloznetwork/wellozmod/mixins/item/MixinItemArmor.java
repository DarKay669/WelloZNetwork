package fr.welloznetwork.wellozmod.mixins.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemArmor.class)
public class MixinItemArmor
{
    @Shadow
    private ArmorMaterial material;
    
    @Overwrite
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        int i = EntityLiving.getArmorPosition(itemStack) - 1;
        ItemStack itemStack1 = entityPlayer.getCurrentArmor(i);
        
        if (itemStack1 == null)
        {
            entityPlayer.setCurrentItemOrArmor(i + 1, itemStack.copy());
            itemStack.stackSize = 0;
            
            if (this.material == ArmorMaterial.CHAIN)
            {
                world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_ARMOR_EQUIP_CHAIN[world.rand.nextInt(RegistrySounds.ITEM_ARMOR_EQUIP_CHAIN.length)], 1.0F, 1.0F);
            }
            
            else if (this.material == ArmorMaterial.DIAMOND)
            {
                world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_ARMOR_EQUIP_DIAMOND[world.rand.nextInt(RegistrySounds.ITEM_ARMOR_EQUIP_DIAMOND.length)], 1.0F, 1.0F);
            }
            
            else if (this.material == ArmorMaterial.GOLD)
            {
                world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_ARMOR_EQUIP_GOLD[world.rand.nextInt(RegistrySounds.ITEM_ARMOR_EQUIP_GOLD.length)], 1.0F, 1.0F);
            }
            
            else if (this.material == ArmorMaterial.IRON)
            {
                world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_ARMOR_EQUIP_IRON[world.rand.nextInt(RegistrySounds.ITEM_ARMOR_EQUIP_IRON.length)], 1.0F, 1.0F);
            }
            
            else if (this.material == ArmorMaterial.CLOTH)
            {
                world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_ARMOR_EQUIP_LEATHER[world.rand.nextInt(RegistrySounds.ITEM_ARMOR_EQUIP_LEATHER.length)], 1.0F, 1.0F);
            }
            
            else
            {
                world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, RegistrySounds.ITEM_ARMOR_EQUIP_GENERIC[world.rand.nextInt(RegistrySounds.ITEM_ARMOR_EQUIP_GENERIC.length)], 1.0F, 1.0F);
            }
        }
        
        return itemStack;
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return this.material == ArmorMaterial.CHAIN ? EnumRarity.uncommon : EnumRarity.common;
    }
}