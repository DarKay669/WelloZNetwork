package fr.welloznetwork.wellozmod.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemGoatHorn extends Item
{
    private static final int PONDER = 0, SING = 1, SEEK = 2, FEEL = 3, ADMIRE = 4, CALL = 5, YEARN = 6, DREAM = 7;
    private int type;
    
    public ItemGoatHorn(String name, String texture, int type)
    {
        setUnlocalizedName(name);
        setTextureName("wellozmod:" + texture);
        setMaxStackSize(1);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.type = type;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List tooltip, boolean advanced)
    {        
        switch (this.type)
        {
            case 0:
                tooltip.add(I18n.format("horn_ponder"));
                break;
                
            case 1:
                tooltip.add(I18n.format("horn_sing"));
                break;
                
            case 2:
                tooltip.add(I18n.format("horn_seek"));
                break;
                
            case 3:
                tooltip.add(I18n.format("horn_feel"));
                break;
                
            case 4:
                tooltip.add(I18n.format("horn_admire"));
                break;
                
            case 5:
                tooltip.add(I18n.format("horn_call"));
                break;
                
            case 6:
                tooltip.add(I18n.format("horn_yearn"));
                break;
                
            case 7:
                tooltip.add(I18n.format("horn_dream"));
        }
    }
    
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slotIndex, boolean heldItem)
    {
        if (itemStack.hasTagCompound())
        {
            if (itemStack.stackTagCompound.getInteger("cooldown") > 0)
            {
                itemStack.stackTagCompound.setInteger("cooldown", itemStack.stackTagCompound.getInteger("cooldown") + 1);
            }
            
            if (itemStack.stackTagCompound.getInteger("cooldown") >= 80)
            {
                itemStack.stackTagCompound.setInteger("cooldown", 0);
            }
        }
        
        super.onUpdate(itemStack, world, entity, slotIndex, heldItem);
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (!world.isRemote)
        {
            if (!itemStack.hasTagCompound())
            {
                itemStack.setTagCompound(new NBTTagCompound());
                itemStack.stackTagCompound.setInteger("cooldown", 0);
            }
            
            if (itemStack.stackTagCompound.getInteger("cooldown") == 0)
            {
                switch (this.type) 
                {
                    case PONDER:
                        world.playSoundAtEntity(entityPlayer, RegistrySounds.SOUND_TYPE_GOAT_HORN_PONDER, 1.0F, 1.0F);
                        break;
                        
                    case SING:
                        world.playSoundAtEntity(entityPlayer, RegistrySounds.SOUND_TYPE_GOAT_HORN_SING, 1.0F, 1.0F);
                        break;
                        
                    case SEEK:
                        world.playSoundAtEntity(entityPlayer, RegistrySounds.SOUND_TYPE_GOAT_HORN_SEEK, 1.0F, 1.0F);
                        break;
                        
                    case FEEL:
                        world.playSoundAtEntity(entityPlayer, RegistrySounds.SOUND_TYPE_GOAT_HORN_FEEL, 1.0F, 1.0F);
                        break;
                        
                    case ADMIRE:
                        world.playSoundAtEntity(entityPlayer, RegistrySounds.SOUND_TYPE_GOAT_HORN_ADMIRE, 1.0F, 1.0F);
                        break;
                        
                    case CALL:
                        world.playSoundAtEntity(entityPlayer, RegistrySounds.SOUND_TYPE_GOAT_HORN_CALL, 1.0F, 1.0F);
                        break;
                        
                    case YEARN:
                        world.playSoundAtEntity(entityPlayer, RegistrySounds.SOUND_TYPE_GOAT_HORN_YEARN, 1.0F, 1.0F);
                        break;
                        
                    case DREAM:
                        world.playSoundAtEntity(entityPlayer, RegistrySounds.SOUND_TYPE_GOAT_HORN_DREAM, 1.0F, 1.0F);
                }
                
                itemStack.stackTagCompound.setInteger("cooldown", 1);
            }
        }
        
        return itemStack;
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return EnumRarity.uncommon;
    }
}