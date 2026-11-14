package fr.welloznetwork.wellozmod.entity.monster.huge;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;

public class EntityHugeSkeleton extends EntitySkeleton
{
    public EntityHugeSkeleton(World world)
    {
        super(world);
        this.yOffset *= 6.0F;
        this.setSize(this.width * 6.0F, this.height * 6.0F);
    }
}