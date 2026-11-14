package fr.welloznetwork.wellozmod.mixins.block;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fr.welloznetwork.wellozmod.Constants;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.world.World;

@Mixin(Block.class)
public class MixinBlock
{
    @Shadow
    @Final
    public static RegistryNamespaced blockRegistry;
    
    @Overwrite
    public static Block getBlockById(int blockId)
    {
        if (blockId == 0)
        {
            return Blocks.air;
        }
        
        Block ret = (Block) blockRegistry.getObjectById(blockId);
        
        return ret == null ? Blocks.air : ret;
    }
    
    @Inject(method = "onBlockActivated", at = @At("HEAD"), cancellable = true)
    private void onBlockRightClick(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (Constants.V2)
        {
            if (this.isSign(world, x, y, z, entityPlayer))
            {
                callbackInfoReturnable.setReturnValue(true);
            }
        }
    }
    
    private boolean isSign(World world, int x, int y, int z, EntityPlayer entityPlayer)
    {
        Block block = (Block) (Object) this;
        
        if (!(block == Blocks.standing_sign || block == Blocks.wall_sign))
        {
            return false;
        }
        
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        
        if (!(tileEntity instanceof TileEntitySign))
        {
            return false;
        }
        
        if (!world.isRemote)
        {
            TileEntitySign tileEntitySign = (TileEntitySign) tileEntity;
            
            tileEntitySign.setEditable(true);
            tileEntitySign.func_145912_a(entityPlayer);
            
            entityPlayer.func_146100_a(tileEntitySign);
            
            return true;
        }
        
        return true;
    }
}