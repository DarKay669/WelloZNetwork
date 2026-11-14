package fr.welloznetwork.wellozmod.mixins.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.world.World;

@Mixin(EntityItemFrame.class)
public class MixinEntityItemFrame extends EntityHanging
{
    public MixinEntityItemFrame(World world)
    {
        super(world);
    }
    
    public int getWidthPixels()
    {
        return 9;
    }
    
    public int getHeightPixels()
    {
        return 9;
    }
    
    public void onBroken(Entity entity) {}
    
    public void moveEntity(double x, double y, double z) {}
    
    @Inject(method = "func_146065_b", at = @At("HEAD"), cancellable = true)
    public void attackEntityFrom(Entity entity, boolean drop, CallbackInfo callbackInfo)
    {
        if (drop && entity == null)
        {
            callbackInfo.cancel();
            return;
        }
    }
}