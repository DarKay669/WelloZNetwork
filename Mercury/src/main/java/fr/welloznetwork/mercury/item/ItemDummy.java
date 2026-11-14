package fr.welloznetwork.mercury.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDummy extends Item
{
    private int serverType;
    
    public ItemDummy(String unlocalizedName, String textureName, int serverType)
    {
        setUnlocalizedName(unlocalizedName);
        setTextureName("mercury:" + textureName);
        setMaxStackSize(1);
        
        this.serverType = serverType;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean advanced)
    {
        list.add("");
        
        switch (this.serverType)
        {
            case 0:
                list.add(I18n.format("item.faction.information"));
                break;
                
            case 1:
                list.add(I18n.format("item.minage.information"));
                break;
        }
    }
}