package fr.welloznetwork.wellozmod.mixins.entity.projectile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.world.World;

@Mixin(EntityThrowable.class)
public abstract class MixinEntityThrowable extends Entity
{
    public MixinEntityThrowable(World world)
    {
        super(world);
    }
    
    @Inject(method = "onUpdate", at = @At("TAIL"))
    public void checkCollide(CallbackInfo callbackInfo)
    {
        func_145775_I();
    }
}