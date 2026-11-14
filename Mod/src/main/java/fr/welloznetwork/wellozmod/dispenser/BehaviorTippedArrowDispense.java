package fr.welloznetwork.wellozmod.dispenser;

import fr.welloznetwork.wellozmod.entity.projectile.EntityArrowTipped;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BehaviorTippedArrowDispense extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(IBlockSource iBlockSource, final ItemStack itemStack)
    {
        return new BehaviorProjectileDispense()
        {
            protected IProjectile getProjectileEntity(World world, IPosition iPosition)
            {
                EntityArrowTipped entityArrowTipped = new EntityArrowTipped(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
                entityArrowTipped.canBePickedUp = 1;
                entityArrowTipped.setArrow(itemStack);
                
                return entityArrowTipped;
            }
        }.dispense(iBlockSource, itemStack);
    }
    
    protected void playDispenseSound(IBlockSource iBlockSource)
    {
        iBlockSource.getWorld().playAuxSFX(1002, iBlockSource.getXInt(), iBlockSource.getYInt(), iBlockSource.getZInt(), 0);
    }
}