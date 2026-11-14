package fr.welloznetwork.wellozmod.mixins.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

@Mixin(Enchantment.class)
public abstract class MixinEnchantment
{
    @Shadow
    protected String name;
    
    @Shadow
    public abstract int getMaxLevel();
    
    @Shadow
    public abstract String getName();
    
    @Overwrite
    public String getTranslatedName(int level)
    {
        String s = StatCollector.translateToLocal(this.getName());
        
        if (this.isCurse())
        {
            s = EnumChatFormatting.RED + s;
        }
        
        return level == 1 && this.getMaxLevel() == 1 ? s : s + " " + StatCollector.translateToLocal("enchantment.level." + level);
    }
    
    public boolean isCurse()
    {
        return false;
    }
}