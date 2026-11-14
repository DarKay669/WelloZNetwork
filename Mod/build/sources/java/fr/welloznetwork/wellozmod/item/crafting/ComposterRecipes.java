package fr.welloznetwork.wellozmod.item.crafting;

import java.util.List;

import com.google.common.collect.ImmutableList;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import fr.welloznetwork.wellozmod.util.ModUtil.ItemStackMap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ComposterRecipes
{
    private static final ItemStackMap<Integer> COMPOSTING_REGISTRY = new ItemStackMap<Integer>();
    
    public static void init()
    {
        registerCompostable(ImmutableList.of(new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.melon_seeds), new ItemStack(Items.pumpkin_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(RegistryItems.beetroot_seeds), "listAllSeeds", "treeSapling", "treeLeaves", new ItemStack(RegistryItems.sweet_berries)), 30);
        registerCompostable(ImmutableList.of(new ItemStack(Blocks.cactus), new ItemStack(Items.melon), new ItemStack(Items.reeds), new ItemStack(Blocks.double_plant, 1, 2), new ItemStack(Blocks.vine)), 50);
        registerCompostable(ImmutableList.of(new ItemStack(Items.apple), new ItemStack(RegistryItems.beetroot), "cropCarrot", new ItemStack(Items.dye, 1, 3), new ItemStack(Blocks.tallgrass, 1, 2), new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Blocks.double_plant, 1, 1), new ItemStack(Blocks.double_plant, 1, 2), new ItemStack(Blocks.double_plant, 1, 3), new ItemStack(Blocks.double_plant, 1, 4), new ItemStack(Blocks.double_plant, 1, 5), new ItemStack(Blocks.red_flower, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.yellow_flower, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(RegistryBlocks.wither_rose), new ItemStack(Blocks.waterlily), new ItemStack(Blocks.melon_block), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.red_mushroom), new ItemStack(Items.nether_wart), "cropPotato", new ItemStack(Blocks.pumpkin), "cropWheat"), 65);
        registerCompostable(ImmutableList.of(new ItemStack(Items.baked_potato), new ItemStack(Items.bread), new ItemStack(Items.cookie), new ItemStack(Blocks.hay_block), new ItemStack(Blocks.red_mushroom_block, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.brown_mushroom_block, 1, OreDictionary.WILDCARD_VALUE)), 85);
        registerCompostable(ImmutableList.of(new ItemStack(Blocks.cake), new ItemStack(Items.cake), new ItemStack(Items.pumpkin_pie)), 100);
    }
    
    private static void registerCompostable(Object o, int percentage)
    {
        if (percentage <= 0 || percentage > 600)
        {
            throw new IllegalArgumentException("Tried to add a composter entry with percent value " + percentage + " which is not allowed, should be above 0 and equal to or below 100 !");
        }
        
        if (o instanceof ItemStack)
        {
            COMPOSTING_REGISTRY.put(((ItemStack) o).copy(), percentage);
        }
        
        else if (o instanceof String)
        {
            for (ItemStack itemStack : OreDictionary.getOres((String) o))
            {
                COMPOSTING_REGISTRY.put(itemStack.copy(), percentage);
            }
        }
        
        else if (o instanceof Item)
        {
            COMPOSTING_REGISTRY.put(new ItemStack((Item) o, 1, OreDictionary.WILDCARD_VALUE), percentage);
        }
        
        else if (o instanceof Block && Item.getItemFromBlock((Block) o) != null)
        {
            COMPOSTING_REGISTRY.put(new ItemStack(Item.getItemFromBlock((Block) o), 1, OreDictionary.WILDCARD_VALUE), percentage);
        }
        
        else
        {
            throw new IllegalArgumentException("Tried to add " + o + " as a compostable, which is not an ItemStack, Item, Block or String.");
        }
    }
    
    private static void registerCompostable(List<Object> list, int percentage)
    {
        list.forEach(o -> registerCompostable(o, percentage));
    }
    
    private static ItemStackMap<Integer> getComposts()
    {
        return COMPOSTING_REGISTRY;
    }
    
    private static void remove(String oreDictionary)
    {
        for (ItemStack itemStack : OreDictionary.getOres(oreDictionary))
        {
            remove(itemStack);
        }
    }
    
    private static void remove(ItemStack itemStack)
    {
        COMPOSTING_REGISTRY.remove(itemStack);
    }
    
    private static boolean isCompostable(String oreDictionary)
    {
        for (ItemStack itemStack : OreDictionary.getOres(oreDictionary))
        {
            if (isCompostable(itemStack))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private static boolean isCompostable(ItemStack itemStack)
    {
        return getCompostChance(itemStack) > 0;
    }
    
    public static int getCompostChance(ItemStack itemStack)
    {
        Integer percentage = COMPOSTING_REGISTRY.get(itemStack);
        
        if (percentage != null)
        {
            return percentage;
        }
        
        return 0;
    }
}