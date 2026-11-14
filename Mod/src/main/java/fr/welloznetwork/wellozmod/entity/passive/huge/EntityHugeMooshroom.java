package fr.welloznetwork.wellozmod.entity.passive.huge;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.world.World;

public class EntityHugeMooshroom extends EntityMooshroom
{
    public EntityHugeMooshroom(World world)
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