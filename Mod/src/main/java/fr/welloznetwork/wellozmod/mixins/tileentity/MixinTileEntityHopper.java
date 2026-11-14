package fr.welloznetwork.wellozmod.mixins.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import fr.welloznetwork.wellozmod.tileentity.IFakeTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.World;

@Mixin(TileEntityHopper.class)
public class MixinTileEntityHopper
{
    @Redirect(method = "func_145893_b", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTileEntity(III)Lnet/minecraft/tileentity/TileEntity;", ordinal = 0))
    private static TileEntity getTileEntity(World world, int x, int y, int z)
    {
        if (world.getTileEntity(x, y, z) == null)
        {
            if (world.getBlock(x, y, z) instanceof IFakeTileEntity)
            {
                return ((IFakeTileEntity) world.getBlock(x, y, z)).getTileEntity(world, x, y, z);
            }
        }
        
        return world.getTileEntity(x, y, z);
    }
}