package fr.welloznetwork.wellozmod.mixins.network;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistryPotions;
import fr.welloznetwork.wellozmod.mixins.util.IAxisAlignedBB;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

@Mixin(NetHandlerPlayServer.class)
public class MixinNetHandlerPlayServer
{
    @Shadow 
    @Final 
    private MinecraftServer serverController;
    
    @Shadow 
    public EntityPlayerMP playerEntity;
    
    private boolean field_184344_B;
    
    @Inject(method = "processPlayer(Lnet/minecraft/network/play/client/C03PacketPlayer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayerMP;handleFalling(DZ)V", shift = Shift.AFTER))
    private void levitationEffect(C03PacketPlayer packet, CallbackInfo callbackInfo)
    {
        if (this.playerEntity == null || this.playerEntity.getBoundingBox() == null)
        {
            return;
        }
        
        WorldServer worldServer = this.serverController.worldServerForDimension(this.playerEntity.dimension);     
        
        this.playerEntity.onGround = packet.func_149465_i();
        this.field_184344_B = true;
        this.field_184344_B &= !this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying;
        this.field_184344_B &= !this.playerEntity.isPotionActive(RegistryPotions.LEVITATION) && !worldServer.checkBlockCollision(((IAxisAlignedBB) this.playerEntity.getBoundingBox()).func_186662_g(0.0625D).addCoord(0.0D, -0.55D, 0.0D));
    }
    
    @Inject(method = "processClickWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Container;slotClick(IIILnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void skipClickForSpectators(C0EPacketClickWindow packet, CallbackInfo callbackInfo)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfo.cancel();
            
            ArrayList<ItemStack> arrayList = new ArrayList<>();
            
            for (int i = 0; i < this.playerEntity.openContainer.inventorySlots.size(); i++)
            {
                arrayList.add(((Slot) this.playerEntity.openContainer.inventorySlots.get(i)).getStack());
            }
            
            this.playerEntity.sendContainerAndContentsToPlayer(this.playerEntity.openContainer, arrayList);
        }
    }
    
    @ModifyVariable(method = "processPlayer", at = @At("STORE"), ordinal = 10, name = "d10")
    private double adjustMovementDelta(double d)
    {
        if (this.playerEntity instanceof IEntityPlayer && ((IEntityPlayer) this.playerEntity).isElytraFlying())
        {
            if (this.playerEntity.worldObj.getGameRules().getGameRuleBooleanValue("disableElytraMovementCheck"))
            {
                return 0;
            }
            
            return d / 3;
        }
        
        return d;
    }
}