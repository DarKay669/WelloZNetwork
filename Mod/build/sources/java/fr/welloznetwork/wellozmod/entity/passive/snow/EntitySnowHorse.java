package fr.welloznetwork.wellozmod.entity.passive.snow;

import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntitySnowHorse extends EntityHorse
{
    public EntitySnowHorse(World world)
    {
        super(world);
    }
    
    protected Item getDropItem()
    {
        return RegistryItems.raw_venison;
    }
}