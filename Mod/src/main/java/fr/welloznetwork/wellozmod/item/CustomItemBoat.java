package fr.welloznetwork.wellozmod.item;

import java.util.List;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.entity.item.CustomEntityBoat;
import fr.welloznetwork.wellozmod.entity.item.CustomEntityBoat.BoatType;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Custom(ItemBoat.class)
public class CustomItemBoat extends Item
{
    private final BoatType boatType;
    
    public CustomItemBoat(String unlocalizedName, String textureName, BoatType boatType)
    {
        setMaxStackSize(1);
        setUnlocalizedName(unlocalizedName);
        setTextureName("wellozmod:" + textureName);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        
        this.boatType = boatType;
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        float f = 1.0F;
        float f1 = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * f;
        float f2 = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * f;
        double d0 = entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * (double) f;
        double d1 = entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * (double) f + 1.62D - (double) entityPlayer.yOffset;
        double d2 = entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * (double) f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        MovingObjectPosition movingObjectPosition = world.rayTraceBlocks(vec3, vec31, true);
        
        if (movingObjectPosition == null)
        {
            return itemStack;
        }
        
        Vec3 vec32 = entityPlayer.getLook(f);
        boolean flag = false;
        float f9 = 1.0F;
        List list = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
        int i;
        
        for (i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity) list.get(i);
            
            if (entity.canBeCollidedWith())
            {
                float f10 = entity.getCollisionBorderSize();
                AxisAlignedBB axisAlignedBB = entity.boundingBox.expand(f10, f10, f10);
                
                if (axisAlignedBB.isVecInside(vec3))
                {
                    flag = true;
                }
            }
        }
        
        if (flag)
        {
            return itemStack;
        }
        
        if (movingObjectPosition.typeOfHit == MovingObjectType.BLOCK)
        {
            i = movingObjectPosition.blockX;
            int j = movingObjectPosition.blockY;
            int k = movingObjectPosition.blockZ;
            
            if (world.getBlock(i, j, k) == Blocks.snow_layer)
            {
                j--;
            }
            
            CustomEntityBoat customEntityBoat = new CustomEntityBoat(world);
            boolean isWater = world.getBlock(i, j, k).getMaterial() == Material.water;
            customEntityBoat.setPositionAndRotation(movingObjectPosition.hitVec.xCoord, movingObjectPosition.hitVec.yCoord + (isWater ? -0.12 : 0), movingObjectPosition.hitVec.zCoord, entityPlayer.rotationYaw, 0);
            customEntityBoat.motionX = customEntityBoat.motionY = customEntityBoat.motionZ = 0;
            customEntityBoat.setBoatType(this.boatType);
            
            if (itemStack.hasDisplayName())
            {
                customEntityBoat.setBoatName(itemStack.getDisplayName());
            }
            
            if (!world.getCollidingBoundingBoxes(customEntityBoat, customEntityBoat.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty())
            {
                return itemStack;
            }
            
            if (!world.isRemote)
            {
                world.spawnEntityInWorld(customEntityBoat);
            }
            
            if (!entityPlayer.capabilities.isCreativeMode)
            {
                itemStack.stackSize--;
            }
        }
        
        return itemStack;
    }
}