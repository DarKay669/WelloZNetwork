package fr.welloznetwork.mercury.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.mercury.Mercury;
import fr.welloznetwork.mercury.init.RegistryGui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMap extends Item
{
    public ItemMap()
    {
        setUnlocalizedName("map");
        setTextureName("mercury:map");
        setMaxStackSize(1);
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (world.isRemote)
        {
            entityPlayer.openGui(Mercury.instance, RegistryGui.MAP, world, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
        }
        
        return itemStack;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean advanced)
    {
        list.add("");
        list.add(I18n.format("item.map.information"));
    }
    
    public boolean onDroppedByPlayer(ItemStack itemStack, EntityPlayer entityPlayer)
    {
        return false;
    }
}