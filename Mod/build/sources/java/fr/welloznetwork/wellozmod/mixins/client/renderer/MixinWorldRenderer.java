package fr.welloznetwork.wellozmod.mixins.client.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.WorldRenderer;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer
{
    @Redirect(method = "updateRenderer", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getRenderType()I", ordinal = 0))
    private int skipInsideRenderForSpectator(Block block)
    {
        if (CommandSpectator.isSpectator())
        {
            return -1;
        }
        
        return block.getRenderType();
    }
}