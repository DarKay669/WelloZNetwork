package fr.welloznetwork.wellozmod.item.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class SmithingTableRecipes
{
    private static final SmithingTableRecipes INSTANCE = new SmithingTableRecipes();
    private final List<SmithingTableRecipe> recipes = new ArrayList<SmithingTableRecipe>();
    
    public static SmithingTableRecipes getInstance()
    {
        return INSTANCE;
    }
    
    public static void init()
    {
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_pickaxe), RegistryItems.netherite, new ItemStack(Items.diamond_pickaxe, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_pickaxe), RegistryItems.netherite, new ItemStack(Items.diamond_pickaxe, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_axe), RegistryItems.netherite, new ItemStack(Items.diamond_axe, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_shovel), RegistryItems.netherite, new ItemStack(Items.diamond_shovel, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_sword), RegistryItems.netherite, new ItemStack(Items.diamond_sword, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_hoe), RegistryItems.netherite, new ItemStack(Items.diamond_hoe, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_helmet), RegistryItems.netherite, new ItemStack(Items.diamond_helmet, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_chestplate), RegistryItems.netherite, new ItemStack(Items.diamond_chestplate, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_leggings), RegistryItems.netherite, new ItemStack(Items.diamond_leggings, 1, OreDictionary.WILDCARD_VALUE));
        SmithingTableRecipes.getInstance().addRecipe(new ItemStack(RegistryItems.netherite_boots), RegistryItems.netherite, new ItemStack(Items.diamond_boots, 1, OreDictionary.WILDCARD_VALUE));
    }
    
    public void addRecipe(ItemStack itemStack, Object materialSlot, Object toolSlot)
    {
        INSTANCE.recipes.add(new SmithingTableRecipe(toolSlot, materialSlot, itemStack, true));
    }
    
    public void addRecipeNoNBT(ItemStack itemStack, Object materialSlot, Object toolSlot)
    {
        INSTANCE.recipes.add(new SmithingTableRecipe(toolSlot, materialSlot, itemStack, false));
    }
    
    public void addRecipe(SmithingTableRecipe smithingTableRecipes)
    {
        INSTANCE.recipes.add(smithingTableRecipes);
    }
    
    public List<SmithingTableRecipe> getRecipes()
    {
        return this.recipes;
    }
    
    public ItemStack findMatchingRecipe(InventoryCrafting inventoryCrafting, World world)
    {
        for (SmithingTableRecipe smithingTableRecipe : this.recipes)
        {
            if (smithingTableRecipe.matches(inventoryCrafting, world))
            {
                return smithingTableRecipe.getCraftingResult(inventoryCrafting);
            }
        }
        
        return null;
    }
    
    public static class SmithingTableRecipe implements IRecipe
    {
        private final boolean copyNBT;
        private final Object input, material;
        private final ItemStack output;
        
        public SmithingTableRecipe(Object toolSlot, Object materialSlot, ItemStack itemStack, boolean copy)
        {
            copyNBT = copy;
            output = itemStack;
            Object[] recipe = new Object[]{toolSlot, materialSlot};

            for (int idx = 0; idx < 2; idx++) {
                Object in = recipe[idx];

                if (in instanceof ItemStack)
                {
                    recipe[idx] = ((ItemStack) in).copy();
                } 
                
                else if (in instanceof Item) 
                {
                    recipe[idx] = new ItemStack((Item) in);
                }
                
                else if (in instanceof Block) 
                {
                    recipe[idx] = new ItemStack((Block) in, 1, OreDictionary.WILDCARD_VALUE);
                }
                
                else if (in instanceof String)
                {
                    recipe[idx] = OreDictionary.getOres((String) in);
                } 
                
                else 
                {
                    String ret = "Invalid smithing table recipe: ";
                    
                    for (Object tmp : recipe) 
                    {
                        ret += tmp + ", ";
                    }
                    
                    ret += output;
                    throw new RuntimeException(ret);
                }
            }

            input = recipe[0];
            material = recipe[1];
        }

        public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) 
        {
            ItemStack stack = output.copy();
            
            if (copyNBT)
            {
                ItemStack toCopy = inventoryCrafting.getStackInSlot(0);
                
                if (toCopy.hasTagCompound())
                {
                    stack.setTagCompound(toCopy.getTagCompound());
                }
                
                if (toCopy.isItemStackDamageable() && stack.isItemStackDamageable())
                {
                    stack.setItemDamage(toCopy.getItemDamage());
                }
            }
            
            return stack;
        }

        public int getRecipeSize() 
        {
            return 2;
        }

        public ItemStack getRecipeOutput() 
        {
            return output;
        }

        public Object getInputItem() 
        {
            return input;
        }

        public Object getMaterial() 
        {
            return material;
        }

        public boolean matches(InventoryCrafting inventoryCrafting, World world)
        {
            Object[] recipe = new Object[]{input, material};
            
            for (int x = 0; x < 2; x++)
            {
                Object target;
                target = recipe[x];
                ItemStack slot = inventoryCrafting.getStackInSlot(x);

                if (target instanceof ItemStack) 
                {
                    if (!OreDictionary.itemMatches((ItemStack) target, slot, false)) 
                    {
                        return false;
                    }
                    
                } 
                
                else if (target instanceof ArrayList) 
                {
                    boolean matched = false;

                    Iterator<ItemStack> itr = ((ArrayList<ItemStack>) target).iterator();
                    
                    while (itr.hasNext() && !matched)
                    {
                        matched = OreDictionary.itemMatches(itr.next(), slot, false);
                    }

                    if (!matched) 
                    {
                        return false;
                    }
                }
                
                else if (target == null && slot != null)
                {
                    return false;
                }
            }

            return true;
        }
    }
}