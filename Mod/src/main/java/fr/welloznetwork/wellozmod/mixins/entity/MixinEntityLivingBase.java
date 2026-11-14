package fr.welloznetwork.wellozmod.mixins.entity;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.enchantment.EnchantmentDepthStrider;
import fr.welloznetwork.wellozmod.enchantment.EnchantmentSoulSpeed;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity
{    
    public MixinEntityLivingBase(World world)
    {
        super(world);
    }
    
    @Shadow
    @Final
    private HashMap<Integer, PotionEffect> activePotionsMap;
    
    @Inject(method = "moveEntityWithHeading", at = @At("TAIL"))
    private void levitationEffect(float strafe, float forward, CallbackInfo callbackInfo)
    {
        EntityLivingBase entityLivingBase = (EntityLivingBase) (Object) this;
        
        if (entityLivingBase.isPotionActive(RegistryPotions.LEVITATION))
        {
            entityLivingBase.motionY += (0.05D * (double) (entityLivingBase.getActivePotionEffect(RegistryPotions.LEVITATION).getAmplifier() + 1) - entityLivingBase.motionY) * 0.2D;
        }
    }
    
    @ModifyVariable(method = "updatePotionEffects()V", at = @At("STORE"), ordinal = 0)
    private boolean checkIfClient(boolean flag)
    {
        try
        {
            Class<?> clazz = Class.forName("net.minecraft.client.entity.AbstractClientPlayer", false, this.getClass().getClassLoader());
            
            return flag || clazz.isInstance(this);
        }
        
        catch (ClassNotFoundException e)
        {
            return flag;
        }
        
        // return flag || (EntityLivingBase) (Object) this instanceof AbstractClientPlayer;
    }
    
    @Overwrite
    public boolean isPotionActive(int potionId)
    {
        return this.activePotionsMap.size() != 0 && this.activePotionsMap.containsKey(potionId);
    }
    
    @Overwrite
    public boolean isPotionActive(Potion potion)
    {
        return this.activePotionsMap.size() != 0 && this.activePotionsMap.containsKey(potion.id);
    }
    
    public void setAir(int airAmount)
    {
        if (this.getAir() != airAmount)
        {
            super.setAir(airAmount);
        }
    }
    
    @Shadow
    public abstract boolean isClientWorld();
    
    @Inject(method = "moveEntityWithHeading", at = @At("HEAD"), cancellable = true)
    private void moveElytra(float strafe, float forward, CallbackInfo callbackInfo)
    {
        if (this.isClientWorld() && !this.isInWater() && !this.handleLavaMovement())
        {
            if (this instanceof IEntityPlayer && ((IEntityPlayer) this).isElytraFlying())
            {
                if (this.motionY > - 0.5D)
                {
                    this.fallDistance = 1.0F;
                }
                
                Vec3 vec3 = this.getLookVec();
                
                float f = this.rotationPitch * 0.017453292F;
                
                double d6 = Math.sqrt(vec3.xCoord * vec3.xCoord + vec3.zCoord * vec3.zCoord);
                double d8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                double d1 = vec3.lengthVector();
                
                float f4 = MathHelper.cos(f);
                
                f4 = (float) ((double) f4 * (double) f4 * Math.min(1.0D, d1 / 0.4D));
                
                this.motionY += -0.08D + f4 * 0.06D;
                
                if (this.motionY < 0.0D && d6 > 0.0D)
                {
                    double d2 = this.motionY * -0.1D * f4;
                    
                    this.motionY += d2;
                    this.motionX += vec3.xCoord * d2 / d6;
                    this.motionZ += vec3.zCoord * d2 / d6;
                }
                
                if (f < 0.0F)
                {
                    double d9 = d8 * (-MathHelper.sin(f)) * 0.04D;
                    
                    this.motionY += d9 * 3.2D;
                    this.motionX -= vec3.xCoord * d9 / d6;
                    this.motionZ -= vec3.zCoord * d9 / d6;
                }
                
                if (d6 > 0.0D)
                {
                    this.motionX += (vec3.xCoord / d6 * d8 - this.motionX) * 0.1D;
                    this.motionZ += (vec3.zCoord / d6 * d8 - this.motionZ) * 0.1D;
                }
                
                this.motionX *= 0.9900000095367432D;
                this.motionY *= 0.9800000190734863D;
                this.motionZ *= 0.9900000095367432D;
                
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                
                if (this.isCollidedHorizontally && !this.worldObj.isRemote)
                {
                    double d10 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    double d3 = d8 - d10;
                    
                    float f5 = (float) (d3 * 10.0D - 3.0D);
                    
                    if (f5 > 0.0F)
                    {
                        this.playSound((int) f5 > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small", 1.0F, 1.0F);
                        this.attackEntityFrom((new DamageSource("flyIntoWall")).setDamageBypassesArmor(), f5);
                    }
                }
                
                if (this.onGround && !this.worldObj.isRemote)
                {
                    ((IEntityPlayer) this).setElytraFlying(false);
                }
                
                callbackInfo.cancel();
            }
        }
    }
    
    @Inject(method = "moveEntityWithHeading", at = @At("HEAD"), cancellable = true)
    private void getEnchantmentModifier(float strafe, float forward, CallbackInfo callbackInfo)
    {
        if (this.onGround)
        {
            EnchantmentSoulSpeed.getSoulSpeedModifier((EntityLivingBase) (Object) this);
        }
        
        if (this.isInWater())
        {
            EnchantmentDepthStrider.getDepthStriderModifier((EntityLivingBase) (Object) this);
            callbackInfo.cancel();
        }
    }
}