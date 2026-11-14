package fr.welloznetwork.wellozmod.mixins.server.management;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;

@Mixin(ItemInWorldManager.class)
public class MixinItemInWorldManager
{
    @Shadow
    private GameType gameType;
    
    @Inject(method = "onBlockClicked", at = @At(value = "FIELD", target = "Lnet/minecraft/server/management/ItemInWorldManager;curblockDamage:F", opcode = Opcodes.GETFIELD), cancellable = true)
    private void isSpectatorBlockClicked(int x, int y, int z, int side, CallbackInfo callbackInfo)
    {
        if (this.gameType.isAdventure())
        {
            if (CommandSpectator.isSpectator())
            {
                return;
            }
        }
    }
    
    @Inject(method = "tryHarvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getCurrentEquippedItem()Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void isSpectatorTryHarvestBlock(int x, int y, int z, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (this.gameType.isAdventure())
        {
            if (CommandSpectator.isSpectator())
            {
                callbackInfoReturnable.setReturnValue(false);
            }
        }
    }
    
    @Inject(method = "tryUseItem", at = @At("HEAD"), cancellable = true)
    private void isSpectatorTryUseItem(EntityPlayer entityPlayer, World world, ItemStack itemStack, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (CommandSpectator.isSpectator())
        {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Inject(method = "onBlockClicked(IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;destroyBlockInWorldPartially(IIIII)V"))
    private void onBlockClicked(int x, int y, int z, int side, CallbackInfo callbackInfo)
    {
        ((ItemInWorldManager) (Object) this).thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, ((ItemInWorldManager) (Object) this).theWorld));
    }
}