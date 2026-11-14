package fr.welloznetwork.wellozmod.mixins.stats;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatisticsFile;

@Mixin(StatisticsFile.class)
public class MixinStatisticsFile extends StatFileWriter
{
    @Shadow
    private Set field_150888_e;
    
    @Shadow
    private boolean field_150886_g;
    
    @Overwrite
    public void func_150873_a(EntityPlayer entityPlayer, StatBase statBase, int count)
    {
        int j = statBase.isAchievement() ? this.writeStat(statBase) : 0;
        super.func_150873_a(entityPlayer, statBase, count);
        this.field_150888_e.add(statBase);
        
        if (statBase.isAchievement() && j == 0 && count > 0)
        {
            this.field_150886_g = true;
        }
    }
}