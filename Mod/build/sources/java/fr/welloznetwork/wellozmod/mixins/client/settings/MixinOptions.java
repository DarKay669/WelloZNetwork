package fr.welloznetwork.wellozmod.mixins.client.settings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.settings.GameSettings.Options;

@Mixin(Options.class)
public class MixinOptions
{
    @Overwrite
    public static Options getEnumOptions(int ordinal)
    {
        Options[] options = Options.values();
        
        if (ordinal >= options.length | ordinal < 0)
        {
            return null;
        }
        
        else
        {
            return options[ordinal];
        }
    }
}