package fr.welloznetwork.wellozmod.entity.monster;

import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityHerobrine extends EntityMob
{		
	public EntityHerobrine(World world)
	{
		super(world);
	}
	
	public void applyEntityAttributes()
	{
	    super.applyEntityAttributes();
	    this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
	    this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(30.0D);
	    this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1500.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(75.0D);
	}
	
	public float getBlockPathWeight(int x, int y, int z)
	{
	    return this.worldObj.getLightBrightness(x, y, z) - 0.5F;
	}
	
	public ItemStack getHeldItem()
    {
        return new ItemStack(RegistryItems.netherite_sword);
    }
}