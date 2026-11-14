package fr.welloznetwork.wellozmod.mixins.entity.player;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.enchantment.CustomEnchantmentHelper;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.item.ItemElytra;
import fr.welloznetwork.wellozmod.network.play.server.S04PacketElytra;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.world.World;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase implements IEntityPlayer
{
    @Shadow
    public InventoryPlayer inventory;
    
    @Shadow
    public PlayerCapabilities capabilities;
    
    private float ticksElytraFlying = 0;
    private boolean lastElytraFlying = false, lastIsJumping = false;
    
    private static final Set<Achievement> ACHIEVEMENTS = new HashSet<>();
    
    @Shadow
    public abstract boolean isPlayerSleeping();
    
    @Shadow
    protected abstract void setHideCape(int p_82239_1_, boolean p_82239_2_);
    
    public MixinEntityPlayer(World world)
    {
        super(world);
    }
    
    public boolean canBePushed()
    {
        return !CommandSpectator.isSpectator();
    }
    
    public boolean canBeCollidedWith()
    {
        return !CommandSpectator.isSpectator();
    }
    
    protected void collideWithNearbyEntities() 
    {
        if (!CommandSpectator.isSpectator())
        {
            super.collideWithNearbyEntities();
        }
    }
    
    public boolean isOnLadder()
    {
        if (CommandSpectator.isSpectator())
        {
            return false;
        }
        
        return super.isOnLadder();
    }
    
    public boolean handleWaterMovement()
    {
        if (CommandSpectator.isSpectator())
        {
            return false;
        }
        
        return super.handleWaterMovement();
    }
    
    public ItemStack getEquipmentInSlot(int slot)
    {
        return CommandSpectator.isSpectator() ? null : slot == 0 ? this.inventory.getCurrentItem() : this.inventory.armorInventory[slot - 1];
    }
    
    @Inject(method = "isCurrentToolAdventureModeExempt", at = @At(value = "HEAD"), cancellable = true)
    public void isSpectating(int x, int y, int z, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getHealth()F"))
    private float isSpectatorOnLivingUpdate(EntityPlayer entityPlayer)
    {
        return CommandSpectator.isSpectator() ? - 1.0F : entityPlayer.getHealth();
    }
    
    @Inject(method = "interactWith", at = @At("HEAD"), cancellable = true)
    private void isSpectatorInteractWith(Entity entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (CommandSpectator.isSpectator())
        {
            if (entity instanceof IInventory)
            {
                ((EntityPlayer) (Object) this).displayGUIChest((IInventory) entity);
            }
            
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Inject(method = "getEyeHeight", at = @At("HEAD"), cancellable = true)
    private void getElytraEyeHeight(CallbackInfoReturnable<Float> callbackInfoReturnable)
    {
        if (this.isElytraFlying() && !this.isPlayerSleeping())
        {
            callbackInfoReturnable.setReturnValue(0.4F);
        }
    }
    
    @Inject(method = "writeEntityToNBT", at = @At("TAIL"))
    private void writeElytra(NBTTagCompound nbtTagCompound, CallbackInfo callbackInfo)
    {
        nbtTagCompound.setBoolean("FallFlying", this.isElytraFlying());
    }
    
    @Inject(method = "readEntityFromNBT", at = @At("TAIL"))
    private void readElytra(NBTTagCompound nbtTagCompound, CallbackInfo callbackInfo)
    {
        if (nbtTagCompound.getBoolean("FallFlying"))
        {
            this.setElytraFlying(true);
        }
    }
    
    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("TAIL"))
    private void getSweepingEdgeModifier(Entity entity, CallbackInfo callbackInfo)
    {
        if (entity instanceof EntityLivingBase)
        {
            CustomEnchantmentHelper.getSweepingEdgeModifier((EntityPlayer) (Object) this, (EntityLivingBase) entity);
        }
    }
    
    @Inject(method = "onLivingUpdate", at = @At("TAIL"))
    private void startElytraFlying(CallbackInfo callbackInfo)
    {
        if (this.isJumping && !this.lastIsJumping && !this.onGround && (this.motionY < 0.0D) && !((IEntityPlayer) this).isElytraFlying() && !this.capabilities.isFlying)
        {
            ItemStack itemStack = ItemElytra.getElytra(this);
            
            if (itemStack != null && !ItemElytra.isBroken(itemStack))
            {
                WelloZMod.proxy.getNetworkManager().sendToServer(new S04PacketElytra());
            }
        }
        
        this.lastIsJumping = this.isJumping;
    }
    
    public void tickElytra()
    {
        boolean flag = this.isElytraFlying();
        
        ItemStack itemStack = ItemElytra.getElytra(this);
        
        this.setHideCape(1, itemStack != null);
        
        if (itemStack != null && !this.capabilities.isFlying && !ItemElytra.isBroken(itemStack))
        {
            if (flag && !this.onGround && !this.isRiding() && !this.isInWater())
            {
                if (!this.worldObj.isRemote && (this.ticksElytraFlying + 1) % 20 == 0)
                {
                    itemStack.damageItem(1, this);
                }
            }
            
            else
            {
                flag = false;
            }
        }
        
        else
        {
            flag = false;
        }
        
        if (!this.worldObj.isRemote)
        {
            this.setElytraFlying(flag);
        }
        
        if (this.isElytraFlying())
        {
            this.ticksElytraFlying = this.ticksElytraFlying + 1;
        }
        
        else
        {
            this.ticksElytraFlying = 0;
        }
    }
    
    public boolean isElytraFlying()
    {
        return this.getFlag(7);
    }
    
    public void setElytraFlying(boolean state)
    {
        this.setFlag(7, state);
    }
    
    public float getTicksElytraFlying()
    {
        return this.ticksElytraFlying;
    }
    
    public boolean lastElytraFlying()
    {
        return this.lastElytraFlying;
    }
    
    public void setLastElytraFlying(boolean state)
    {
        this.lastElytraFlying = state;
    }
}