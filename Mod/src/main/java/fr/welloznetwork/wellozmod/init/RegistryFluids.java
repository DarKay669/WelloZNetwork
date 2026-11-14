package fr.welloznetwork.wellozmod.init;

import fr.welloznetwork.wellozmod.WelloZMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class RegistryFluids
{
    public static void init()
    {
        FluidContainerRegistry.registerFluidContainer(RegistryBlocks.poison_water_fluid, new ItemStack(RegistryItems.poison_water_bucket), new ItemStack(Items.bucket));
        WelloZMod.proxy.getServerEventRegister().buckets.put(RegistryBlocks.poison_water, RegistryItems.poison_water_bucket);
    }
}