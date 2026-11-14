package fr.welloznetwork.wellozmod.mixins.util;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.FoodStats;

@Mixin(FoodStats.class)
public interface IMixinFoodStatsAccessor
{
    @Accessor("foodLevel")
    void setFoodLevel(int paramFoodLevel);
}