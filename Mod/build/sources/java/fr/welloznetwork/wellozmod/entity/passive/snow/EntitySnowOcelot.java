package fr.welloznetwork.wellozmod.entity.passive.snow;

import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntitySnowOcelot extends EntityOcelot
{
    public EntitySnowOcelot(World world)
    {
        super(world);
    }
    
    protected Item getDropItem()
    {
        return Items.leather;
    }
}