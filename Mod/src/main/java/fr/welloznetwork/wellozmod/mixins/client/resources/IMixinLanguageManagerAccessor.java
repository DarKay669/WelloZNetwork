package fr.welloznetwork.wellozmod.mixins.client.resources;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;

@Mixin(LanguageManager.class)
public interface IMixinLanguageManagerAccessor
{
    @Accessor("languageMap")
    Map<String, Language> getLanguageMap();
}