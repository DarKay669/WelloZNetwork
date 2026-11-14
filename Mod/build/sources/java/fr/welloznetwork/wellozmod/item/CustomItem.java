package fr.welloznetwork.wellozmod.item;

import org.apache.commons.lang3.StringUtils;

import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Custom(Item.class)
public class CustomItem
{
    private Item item;
    private int metadata, registry;
    
    public CustomItem(ItemStack itemStack)
    {
        this.item = itemStack.getItem();
        this.metadata = itemStack.getItemDamage();
        this.registry = Item.getIdFromItem(this.item);
    }
    
    public CustomItem(String entry)
    {
        entry = entry.replaceAll(" ", "");

        this.item = (Item) Item.itemRegistry.getObjectById(this.registry);
        this.metadata = Integer.parseInt(StringUtils.substringBetween(entry, "(", ")"));
        this.registry = Integer.parseInt(StringUtils.substringBetween(entry, "{", "}"));
    }
    
    public Item getItem()
    {
        return this.item;
    }
    
    public int getMetadata()
    {
        return this.metadata;
    }
    
    public int getRegistry()
    {
        return this.registry;
    }
    
    public ItemStack getItemStack()
    {
        return this.getItemStack(1);
    }
    
    public ItemStack getItemStack(int count)
    {
        return new ItemStack(this.item, count, this.metadata);
    }
    
    public String toString()
    {
        return "{" + this.registry + "}(" + this.metadata + ")";
    }
    
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }
        
        if (!(o instanceof CustomItem))
        {
            return false;
        }
        
        CustomItem customItem = (CustomItem) o;
        
        return this.registry == customItem.registry && this.metadata == customItem.metadata && this.item == customItem.item;
    }
}