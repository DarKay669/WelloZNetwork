package fr.welloznetwork.wellozmod.entity.passive.huge;

import fr.welloznetwork.wellozmod.entity.passive.EntityRabbit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityHugeRabbit extends EntityRabbit
{
    public EntityHugeRabbit(World world)
    {
        super(world);
        this.yOffset *= 6.0F;
        this.setSize(this.width * 6.0F, this.height * 6.0F);
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }
}