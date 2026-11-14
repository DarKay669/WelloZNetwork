package fr.welloznetwork.wellozmod.world.storage.loot;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class LootTableManager
{
    public static final ChestGenHooks COMPOSTER_LOOT = ChestGenHooks.getInfo("composting");
    
    public static void postInit()
    {
        ChestGenHooks.getInfo("composting").setMin(1);
        ChestGenHooks.getInfo("composting").setMax(1);
        COMPOSTER_LOOT.addItem(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 15), 1, 1, 10));
    }
}