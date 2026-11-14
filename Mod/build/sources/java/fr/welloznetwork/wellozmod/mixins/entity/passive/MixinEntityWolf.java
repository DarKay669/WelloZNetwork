package fr.welloznetwork.wellozmod.mixins.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.entity.passive.EntityFox;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;

@Mixin(EntityWolf.class)
public abstract class MixinEntityWolf extends EntityTameable
{
    public MixinEntityWolf(World world)
    {
        super(world);
    }
    
    @Inject(method = "<init>*", at = @At("RETURN"))
    public void postConstructed(CallbackInfo callbackInfo)
    {
        this.targetTasks.addTask(this.targetTasks.taskEntries.size(), new EntityAITargetNonTamed((EntityWolf) (Object) this, EntityFox.class, 200, false));
    }
}