package fr.welloznetwork.wellozmod.mixins.entity;

import java.util.HashMap;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import cpw.mods.fml.common.FMLLog;
import fr.welloznetwork.wellozmod.util.ModUtil.RandomXoshiro256StarStar;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;

@Mixin(Entity.class)
public class MixinEntity
{
    @Shadow
    public boolean noClip;
    
    @Redirect(method = "<init>*", at = @At(value = "NEW", target = "java/util/Random"))
    private Random redirectInitRandom()
    {
        return new RandomXoshiro256StarStar();
    }
    
    @Shadow(remap = false)
    protected HashMap<String, IExtendedEntityProperties> extendedProperties;
    
    @Inject(method = "isEntityInsideOpaqueBlock", at = @At("HEAD"), cancellable = true)
    private void ignoreBlockIfSpectator(CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (this.noClip)
        {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Overwrite(remap = false)
    public String registerExtendedProperties(String identifier, IExtendedEntityProperties iExtendedEntityProperties)
    {
        if (identifier == null)
        {
            FMLLog.warning("Someone is attempting to register extended properties using a null identifier. This is not allowed. Aborting. This may have caused instability");
            return "";
        }
        
        if (iExtendedEntityProperties == null)
        {
            FMLLog.warning("Someone is attempting to register null extended properties. This is not allowed. Aborting. This may have caused instability");
            return "";
        }
        
        String baseIdentifier = identifier;
        int identifierModCount = 1;
        
        while (this.extendedProperties.containsKey(identifier))
        {
            identifier = String.format("%s%d", baseIdentifier, identifierModCount++);
        }
        
        this.extendedProperties.put(identifier, iExtendedEntityProperties);
        
        return identifier;
    }
}