package fr.welloznetwork.wellozmod.item.armor;

import org.lwjgl.input.Keyboard;

import fr.welloznetwork.wellozmod.init.RegistryArmorMaterials;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemArmorSlime extends ItemArmor
{    
    public ItemArmorSlime(String unlocalizedName, int type)
    {
        super(RegistryArmorMaterials.SLIME, 0, type);
        setUnlocalizedName(unlocalizedName);
        setTextureName("wellozmod:" + unlocalizedName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_V2);
    }
    
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type)
    {
        if (slot == 2)
        {
            return "wellozmod:textures/models/armors/slime_2.png";
        }
        
        return "wellozmod:textures/models/armors/slime_1.png";
    }
    
    public void onArmorTick(World world, EntityPlayer entityPlayer, ItemStack itemStack)
    {
        switch (this.armorType)
        {
            case 0:
                
                if (world.isRemote)
                {
                    if (world.getBlock(MathHelper.floor_double(entityPlayer.posX), MathHelper.floor_double(entityPlayer.posY + 1.0D), MathHelper.floor_double(entityPlayer.posZ)).getMaterial().isSolid())
                    {
                        if (Keyboard.isKeyDown((Minecraft.getMinecraft()).gameSettings.keyBindJump.getKeyCode()))
                        {
                            entityPlayer.motionY = 0.1D;
                            break;
                        }
                        
                        entityPlayer.fallDistance = 0.0F;
                        entityPlayer.motionY *= 0.6D;
                    }
                }
                
                break;
            
            case 1:
                
                if (entityPlayer.onGround && !entityPlayer.isSneaking() && entityPlayer.motionY < 0.0D)
                {
                    entityPlayer.motionY = 0.4D;
                    entityPlayer.fallDistance = 0.0F;
                    
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer(true));
                }
                
                break;
                
            case 2:
                entityPlayer.addPotionEffect(new PotionEffect(Potion.jump.id, 50, 1));
                break;
                
            case 3:
                
                if (entityPlayer.isSneaking())
                {
                    entityPlayer.fallDistance = 0.0F;
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer(true));
                }
        }
    }
}