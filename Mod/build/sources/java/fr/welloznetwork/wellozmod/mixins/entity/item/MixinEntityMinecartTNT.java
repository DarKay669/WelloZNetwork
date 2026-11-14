package fr.welloznetwork.wellozmod.mixins.entity.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

@Mixin(EntityMinecartTNT.class)
public abstract class MixinEntityMinecartTNT extends EntityMinecart
{
    public MixinEntityMinecartTNT(World world)
    {
        super(world);
    }
    
    @Shadow
    public abstract void explodeCart(double radius);
    
    public boolean attackEntityFrom(DamageSource damageSource, float amount)
    {
        Entity entity = damageSource.getSourceOfDamage();
        
        if (entity instanceof EntityArrow)
        {
            EntityArrow entityArrow = (EntityArrow) entity;
            
            if (entityArrow.isBurning())
            {
                this.explodeCart(entityArrow.motionX * entityArrow.motionX + entityArrow.motionY * entityArrow.motionY + entityArrow.motionZ * entityArrow.motionZ);
            }
        }
        
        return super.attackEntityFrom(damageSource, amount);
    }
}