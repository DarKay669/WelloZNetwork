package fr.welloznetwork.wellozmod.mixins.client.renderer;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.client.listener.ClientListener;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryKeys;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MouseFilter;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer
{
    @Shadow
    private Minecraft mc;
    
    @Shadow
    private MouseFilter mouseFilterXAxis = new MouseFilter();
    
    @Shadow
    private MouseFilter mouseFilterYAxis = new MouseFilter();
    
    @Shadow
    private float debugCamFOV;
    
    @Shadow
    private float prevDebugCamFOV;
    
    @Shadow
    private float fovModifierHand;
    
    @Shadow
    private float fovModifierHandPrev;
    
    @Shadow
    private int debugViewDirection;
    
    @ModifyVariable(method = "orientCamera", at = @At("STORE"), ordinal = 7, name = "d6")
    private double ignoreDistanceForSpectator(double value)
    {
        if (CommandSpectator.isSpectator() && this.mc.renderViewEntity == this.mc.thePlayer)
        {
            return Double.MAX_VALUE;
        }
        
        return value;
    }
    
    @Inject(method = "orientCamera", at = @At("TAIL"))
    private void adjustThirdPersonForElytra(float partialTicks, CallbackInfo callbackInfo)
    {
        if (this.mc.gameSettings.thirdPersonView > 0 && this.mc.renderViewEntity instanceof IEntityPlayer)
        {
            if (((IEntityPlayer) this.mc.renderViewEntity).isElytraFlying())
            {
                GL11.glTranslatef(0.0F, 1.62F - 0.4F, 0.0F);
            }
        }
    }
    
    @Overwrite
    private float getFOVModifier(float partialTicks, boolean useFOVSetting)
    {
        if (this.debugViewDirection > 0)
        {
            return 90.0F;
        }
        
        else
        {
            Entity entity = this.mc.renderViewEntity;
            
            float f = 70.0F;
            
            if (useFOVSetting)
            {
                f = this.mc.gameSettings.fovSetting;
                f *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;;
            }
            
            boolean flag = false;
            
            if (this.mc.currentScreen == null)
            {
                GameSettings gameSettings = this.mc.gameSettings;
                flag = GameSettings.isKeyDown(RegistryKeys.KEY_BIND_ZOOM);
            }
            
            if (flag)
            {
                if (!ClientListener.zoomMode)
                {
                    ClientListener.zoomMode = true;
                    
                    this.mc.gameSettings.smoothCamera = true;
                }
                
                if (ClientListener.zoomMode)
                {
                    f /= 4.0F;
                }
            }
            
            else if (ClientListener.zoomMode)
            {
                ClientListener.zoomMode = false;
                
                this.mc.gameSettings.smoothCamera = false;
                this.mouseFilterXAxis = new MouseFilter();
                this.mouseFilterYAxis = new MouseFilter();
            }
            
            if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getHealth() <= 0.0F)
            {
                float f1 = (float) ((EntityLivingBase) entity).deathTime + partialTicks;
                
                f /= (1.0F - 500.0F / (f1 + 500.0F)) * 2.0F + 1.0F;
            }
            
            Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, (EntityLivingBase) entity, partialTicks);
            
            if (block.getMaterial() == Material.water)
            {
                f = f * 60.0F / 70.0F;
            }
            
            return f + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * partialTicks;
        }
    }
}