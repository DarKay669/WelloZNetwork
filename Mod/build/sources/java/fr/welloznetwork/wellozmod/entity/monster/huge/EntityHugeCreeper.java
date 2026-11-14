package fr.welloznetwork.wellozmod.entity.monster.huge;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;

public class EntityHugeCreeper extends EntityCreeper
{
    public EntityHugeCreeper(World world)
    {
        super(world);
        this.yOffset *= 6.0F;
        this.setSize(this.width * 6.0F, this.height * 6.0F);
    }
}