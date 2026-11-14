package fr.welloznetwork.wellozmod.entity.passive.huge;

import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityHugeSheep extends EntitySheep
{
    public EntityHugeSheep(World world)
    {
        super(world);
        this.yOffset *= 6.0F;
        this.setSize(this.width * 6.0F, this.height * 6.0F);
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
    }
    
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {        
        if (!this.getSheared())
        {
            this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor()), 0.0F);
        }
        
        this.entityDropItem(new ItemStack(RegistryItems.mutton, 1, 0), 0.0F);
    }
    
    protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.wool);
    }
}