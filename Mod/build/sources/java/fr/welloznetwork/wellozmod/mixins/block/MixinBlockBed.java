package fr.welloznetwork.wellozmod.mixins.block;

import java.util.Map;
import java.util.WeakHashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

@Mixin(BlockBed.class)
public class MixinBlockBed extends Block
{
    @Unique
    private static final Map<Entity, Double> BED_BOUNCE_CACHE = new WeakHashMap<>();
    private long lastBounceTick;
    
    protected MixinBlockBed(Material material)
    {
        super(material);
    }
    
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance)
    {
        if (!entity.isSneaking())
        {
            entity.fallDistance /= 2.0F;
            
            if (entity.motionY < 0.0D)
            {
                BED_BOUNCE_CACHE.put(entity, entity.motionY * -0.66D);
                this.lastBounceTick = world.getTotalWorldTime();
            }
        }
        
        super.onFallenUpon(world, x, y, z, entity, fallDistance);
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (this.lastBounceTick == world.getTotalWorldTime())
        {
            Double bounce = BED_BOUNCE_CACHE.remove(entity);
            
            if (bounce != null)
            {
                entity.motionY = bounce;
            }
        }
        
        else
        {
            BED_BOUNCE_CACHE.clear();
        }
        
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
    }
}