package fr.welloznetwork.wellozmod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWarpedFungusOnAStick extends Item
{
    public ItemWarpedFungusOnAStick()
    {
        setUnlocalizedName("warped_fungus_on_a_stick");
        setTextureName("wellozmod:warped_fungus_on_a_stick");
        setMaxStackSize(1);
        setMaxDamage(100);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }
    
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (player.isRiding() && player.ridingEntity instanceof EntityPig)
        {
            EntityPig entityPig = (EntityPig) player.ridingEntity;
            
            if (!world.isRemote)
            {
                if (stack.stackSize == 0)
                {
                    ItemStack itemStack1 = new ItemStack(Items.fishing_rod);
                    itemStack1.setTagCompound(stack.stackTagCompound);
                    return itemStack1;
                }
            }
        }
        
        return stack;
    }
}