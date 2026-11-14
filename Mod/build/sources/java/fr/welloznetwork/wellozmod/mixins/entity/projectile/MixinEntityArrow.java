package fr.welloznetwork.wellozmod.mixins.entity.projectile;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow extends Entity
{
    @Shadow
    private int field_145791_d;
    
    @Shadow
    private int field_145792_e;
    
    @Shadow
    private int field_145789_f;
    
    @Shadow
    private int inData;
    
    @Shadow
    public abstract void onUpdate();
    
    public MixinEntityArrow(World world)
    {
        super(world);
    }
    
    @Redirect(method = "onUpdate", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/entity/projectile/EntityArrow;inData:I"))
    private int getCurrentStuckInMetadata(EntityArrow entityArrow)
    {
        int metadata = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
        
        if (this.inData != metadata)
        {
            AxisAlignedBB axisAlignedBB = AxisAlignedBB.getBoundingBox(this.posX - 0.06D, this.posY - 0.06D, this.posZ - 0.06D, this.posX + 0.06D, this.posY + 0.06D, this.posZ + 0.06D);
            
            if (!this.worldObj.getCollidingBoundingBoxes(entityArrow, axisAlignedBB).isEmpty())
            {
                return metadata;
            }
        }
        
        return this.inData;
    }
}