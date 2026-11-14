package fr.welloznetwork.wellozmod.mixins.world;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.block.BlockObserver;
import fr.welloznetwork.wellozmod.block.IBlockObserver;
import fr.welloznetwork.wellozmod.command.CommandSpectator;
import fr.welloznetwork.wellozmod.util.ModUtil.RandomXoshiro256StarStar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

@Mixin(World.class)
public abstract class MixinWorld implements IBlockAccess
{
    public Random random = new RandomXoshiro256StarStar();
    
    @Shadow
    public boolean isRemote;
    
    @Shadow
    public abstract boolean blockExists(int x, int y, int z);
    
    @Shadow
    public abstract Block getBlock(int x, int y, int z);
    
    private int observedX, observedY, observedZ;
    
    private void notifySurroundingObservers(int x, int y, int z, Block block, int side)
    {
        this.observedX = x;
        this.observedY = y;
        this.observedZ = z;
        
        if (side != 4 && this.blockExists(x - 1, y, z))
        {
            this.notifyObserver(x -1, y, z, block);
        }
        
        if (side != 5 && this.blockExists(x + 1, y, z))
        {
            this.notifyObserver(x + 1, y, z, block);
        }
        
        if (side != 0)
        {
            this.notifyObserver(x, y - 1, z, block);
        }
        
        if (side != 1)
        {
            this.notifyObserver(x, y + 1, z, block);
        }
        
        if (side != 2 && this.blockExists(x, y, z - 1))
        {
            this.notifyObserver(x, y, z - 1, block);
        }
        
        if (side != 3 && this.blockExists(x, y, z + 1))
        {
            this.notifyObserver(x, y, z + 1, block);
        }
    }
    
    private void notifyObserver(int x, int y, int z, Block block)
    {
        Block block1 = this.getBlock(x, y, z);
        
        if (block1 instanceof IBlockObserver)
        {
            ((IBlockObserver) block1).observedNeighborChange((World) (Object) this, x, y, z, block, this.observedX, this.observedY, this.observedZ);
        }
    }
    
    @Inject(method = "notifyBlocksOfNeighborChange(IIILnet/minecraft/block/Block;)V", at = @At("TAIL"))
    private void notifyObserversAfterNeighborUpdate(int x, int y, int z, Block block, CallbackInfo callbackInfo)
    {
        if (BlockObserver.areNotificationsEnabled())
        {
            this.notifySurroundingObservers(x, y, z, block, -1);
        }
    }
    
    @Inject(method = "notifyBlocksOfNeighborChange(IIILnet/minecraft/block/Block;I)V", at = @At("TAIL"))
    private void notifyObserversAfterNeighborUpdate(int x, int y, int z, Block block, int side, CallbackInfo callbackInfo)
    {
        if (BlockObserver.areNotificationsEnabled())
        {
            this.notifySurroundingObservers(x, y, z, block, side);
        }
    }
    
    private static boolean shouldObserverNotify(int flag)
    {
        return ((flag & 1) != 0 || (flag & 16) == 0);
    }
    
    @Inject(method = "markAndNotifyBlock", at = @At("TAIL"), remap = false)
    private void notifyObserverOnSkippedNeighborUpdate(int x, int y, int z, Chunk chunk, Block block, Block block1, int flag, CallbackInfo callbackInfo)
    {
        if (BlockObserver.areNotificationsEnabled() && !this.isRemote && shouldObserverNotify(flag))
        {
            this.notifySurroundingObservers(x, y, z, block1, -1);
        }
    }
    
    @Inject(method = "setBlockMetadataWithNotify", at = @At("RETURN"))
    private void notifyObserverOnSkippedNeighborUpdate(int x, int y, int z, int metadata, int flag, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (BlockObserver.areNotificationsEnabled() && !this.isRemote && callbackInfoReturnable.getReturnValue() && shouldObserverNotify(flag))
        {
            this.notifySurroundingObservers(x, y, z, this.getBlock(x, y, z), -1);
        }
    }
    
    @Overwrite
    public int getTopSolidOrLiquidBlock(int x, int z)
    {
        Chunk chunk = getChunkFromBlockCoords(x, z);
        
        int chunkX = x & 15;
        int chunkZ = z & 15;
        
        for (int y = chunk.getTopFilledSegment() + 15; y > 0; y--)
        {
            Block block = chunk.getBlock(chunkX, y, chunkZ);
            Material material = block.getMaterial();
            
            if (material.blocksMovement() && !material.isReplaceable() && !block.isLeaves(this, x, y, z) && !block.isFoliage(this, x, y, z) && !block.isWood(this, x, y, z))
            {
                return y + 1;
            }
        }
        
        return -1;
    }
    
    @Shadow
    public abstract Chunk getChunkFromBlockCoords(int x, int z);
    
    @Redirect(method = "getClosestPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getDistanceSq(DDD)D"))
    private double ignoreSpectatorsForClosest(EntityPlayer entityPlayer, double x, double y, double z)
    {
        if (CommandSpectator.isSpectator())
        {
            return Double.MAX_VALUE;
        }
        
        return entityPlayer.getDistanceSq(x, y, z);
    }
    
    @Inject(method = "getClosestPlayer", at = @At("TAIL"), cancellable = true)
    private void neverReturnSpectator(double x, double y, double z, double d, CallbackInfoReturnable<EntityPlayer> callbackInfoReturnable)
    {
        if (callbackInfoReturnable.getReturnValue() != null && CommandSpectator.isSpectator())
        {
            callbackInfoReturnable.setReturnValue(null);
        }
    }
}