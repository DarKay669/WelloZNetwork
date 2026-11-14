package fr.welloznetwork.wellozmod.item;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemMangroveDoor extends Item
{
    public Block mangroveDoor;
    
    public ItemMangroveDoor()
    {
        this.mangroveDoor = RegistryBlocks.mangrove_door;
        setMaxStackSize(1);
        setUnlocalizedName("mangrove_door");
        setTextureName("wellozmod:mangrove_door");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
    }
    
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (side != 1)
        {
            return false;
        }
        
        y++;
        Block block = this.mangroveDoor;
        
        if (entityPlayer.canPlayerEdit(x, y, z, side, itemStack) && entityPlayer.canPlayerEdit(x, y + 1, z, side, itemStack))
        {
            if (!block.canPlaceBlockAt(world, x, y, z))
            {
                return false;
            }
            
            int i1 = MathHelper.floor_double(((entityPlayer.rotationYaw + 180.0F) * 4.0F / 360.0D) - 0.5D) & 0x3;
            ItemDoor.placeDoorBlock(world, x, y, z, i1, block);
            itemStack.stackSize--;
            return true;
        }
        
        return false;
    }
}