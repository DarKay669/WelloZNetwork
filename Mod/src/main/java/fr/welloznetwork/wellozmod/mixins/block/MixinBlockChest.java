package fr.welloznetwork.wellozmod.mixins.block;

import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.util.ModUtil.RandomXoshiro256StarStar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

@Mixin(BlockChest.class)
public abstract class MixinBlockChest extends BlockContainer
{
    @Final
    private Random field_149955_b = new RandomXoshiro256StarStar();
    
    protected MixinBlockChest(Material material)
    {
        super(material);
    }
    
    @Inject(method = "onNeighborBlockChange", at = @At("HEAD"), cancellable = true)
    private void newOnNeighborBlockChange(World world, int x, int y, int z, Block block, CallbackInfo callbackInfo)
    {
        if (!(world.getTileEntity(x, y, z) instanceof TileEntityChest))
        {
            callbackInfo.cancel();
        }
    }
    
    @Overwrite
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        TileEntityChest tileEntityChest = (TileEntityChest) world.getTileEntity(x, y, z);
        
        if (tileEntityChest != null)
        {
            for (int slot = 0; slot < tileEntityChest.getSizeInventory(); slot++)
            {
                ItemStack itemStack = tileEntityChest.getStackInSlot(slot);
                
                if (itemStack != null)
                {
                    float x1 = this.field_149955_b.nextFloat() * 0.8F + 0.1F;
                    float y1 = this.field_149955_b.nextFloat() * 0.8F + 0.1F;
                    float z1 = this.field_149955_b.nextFloat() * 0.8F + 0.1F;
                    
                    EntityItem entityItem = new EntityItem(world, (float) x + x1, (float) y + y1, (float) z + z1, itemStack);
                    
                    entityItem.motionX = (float) this.field_149955_b.nextGaussian() * 0.05F;
                    entityItem.motionY = (float) this.field_149955_b.nextGaussian() * 0.05F + 0.2F;
                    entityItem.motionZ = (float) this.field_149955_b.nextGaussian() * 0.05F;
                    
                    if (itemStack.hasTagCompound())
                    {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                    }
                    
                    world.spawnEntityInWorld(entityItem);
                }
            }
            
            world.func_147453_f(x, y, z, block);
        }
        
        super.breakBlock(world, x, y, z, block, metadata);
    }
}