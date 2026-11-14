package fr.welloznetwork.wellozmod.mixins.block;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import fr.welloznetwork.wellozmod.Constants;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

@Mixin(BlockSkull.class)
public abstract class MixinBlockSkull extends BlockContainer
{
    public MixinBlockSkull(Material material)
    {
        super(material);
    }
    
    @Overwrite
    public void func_149965_a(World world, int x, int y, int z, TileEntitySkull tileEntitySkull)
    {
        if (tileEntitySkull.func_145904_a() == 1 && y >= 2 && world.difficultySetting != EnumDifficulty.PEACEFUL && !world.isRemote)
        {
            int l;
            EntityWither entityWither;
            Iterator iterator;
            EntityPlayer entityPlayer;
            int i1;

            for (l = -2; l <= 0; ++l)
            {   
                if (this.isSoulSandOrSoil(world, x, y - 1, z + l) && this.isSoulSandOrSoil(world, x, y - 1, z + l + 1) && this.isSoulSandOrSoil(world, x, y - 2, z + l + 1) && this.isSoulSandOrSoil(world, x, y - 1, z + l + 2) && this.func_149966_a(world, x, y, z + l, 1) && this.func_149966_a(world, x, y, z + l + 1, 1) && this.func_149966_a(world, x, y, z + l + 2, 1))
                {
                    world.setBlockMetadataWithNotify(x, y, z + l, 8, 2);
                    world.setBlockMetadataWithNotify(x, y, z + l + 1, 8, 2);
                    world.setBlockMetadataWithNotify(x, y, z + l + 2, 8, 2);
                    world.setBlock(x, y, z + l, getBlockById(0), 0, 2);
                    world.setBlock(x, y, z + l + 1, getBlockById(0), 0, 2);
                    world.setBlock(x, y, z + l + 2, getBlockById(0), 0, 2);
                    world.setBlock(x, y - 1, z + l, getBlockById(0), 0, 2);
                    world.setBlock(x, y - 1, z + l + 1, getBlockById(0), 0, 2);
                    world.setBlock(x, y - 1, z + l + 2, getBlockById(0), 0, 2);
                    world.setBlock(x, y - 2, z + l + 1, getBlockById(0), 0, 2);

                    if (!world.isRemote)
                    {
                        entityWither = new EntityWither(world);
                        entityWither.setLocationAndAngles((double) x + 0.5D, (double) y - 1.45D, (double) (z + l) + 1.5D, 90.0F, 0.0F);
                        entityWither.renderYawOffset = 90.0F;
                        entityWither.func_82206_m();

                        if (!world.isRemote)
                        {
                            iterator = world.getEntitiesWithinAABB(EntityPlayer.class, entityWither.boundingBox.expand(50.0D, 50.0D, 50.0D)).iterator();

                            while (iterator.hasNext())
                            {
                                entityPlayer = (EntityPlayer) iterator.next();
                                entityPlayer.triggerAchievement(AchievementList.field_150963_I);
                            }
                        }

                        world.spawnEntityInWorld(entityWither);
                    }

                    for (i1 = 0; i1 < 120; ++i1)
                    {
                        world.spawnParticle("snowballpoof", (double)x + world.rand.nextDouble(), (double)(y - 2) + world.rand.nextDouble() * 3.9D, (double)(z + l + 1) + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    world.notifyBlockChange(x, y, z + l, getBlockById(0));
                    world.notifyBlockChange(x, y, z + l + 1, getBlockById(0));
                    world.notifyBlockChange(x, y, z + l + 2, getBlockById(0));
                    world.notifyBlockChange(x, y - 1, z + l, getBlockById(0));
                    world.notifyBlockChange(x, y - 1, z + l + 1, getBlockById(0));
                    world.notifyBlockChange(x, y - 1, z + l + 2, getBlockById(0));
                    world.notifyBlockChange(x, y - 2, z + l + 1, getBlockById(0));
                    return;
                }
            }

            for (l = -2; l <= 0; ++l)
            {
                if (this.isSoulSandOrSoil(world, x + l, y - 1, z) && this.isSoulSandOrSoil(world, x + l + 1, y - 1, z) && this.isSoulSandOrSoil(world, x + l + 1, y - 2, z) && this.isSoulSandOrSoil(world, x + l + 2, y - 1, z) && this.func_149966_a(world, x + l, y, z, 1) && this.func_149966_a(world, x + l + 1, y, z, 1) && this.func_149966_a(world, x + l + 2, y, z, 1))
                {
                    world.setBlockMetadataWithNotify(x + l, y, z, 8, 2);
                    world.setBlockMetadataWithNotify(x + l + 1, y, z, 8, 2);
                    world.setBlockMetadataWithNotify(x + l + 2, y, z, 8, 2);
                    world.setBlock(x + l, y, z, getBlockById(0), 0, 2);
                    world.setBlock(x + l + 1, y, z, getBlockById(0), 0, 2);
                    world.setBlock(x + l + 2, y, z, getBlockById(0), 0, 2);
                    world.setBlock(x + l, y - 1, z, getBlockById(0), 0, 2);
                    world.setBlock(x + l + 1, y - 1, z, getBlockById(0), 0, 2);
                    world.setBlock(x + l + 2, y - 1, z, getBlockById(0), 0, 2);
                    world.setBlock(x + l + 1, y - 2, z, getBlockById(0), 0, 2);

                    if (!world.isRemote)
                    {
                        entityWither = new EntityWither(world);
                        entityWither.setLocationAndAngles((double) (x + l) + 1.5D, (double) y - 1.45D, (double) z + 0.5D, 0.0F, 0.0F);
                        entityWither.func_82206_m();

                        if (!world.isRemote)
                        {
                            iterator = world.getEntitiesWithinAABB(EntityPlayer.class, entityWither.boundingBox.expand(50.0D, 50.0D, 50.0D)).iterator();

                            while (iterator.hasNext())
                            {
                                entityPlayer = (EntityPlayer) iterator.next();
                                entityPlayer.triggerAchievement(AchievementList.field_150963_I);
                            }
                        }

                        world.spawnEntityInWorld(entityWither);
                    }

                    for (i1 = 0; i1 < 120; ++i1)
                    {
                        world.spawnParticle("snowballpoof", (double)(x + l + 1) + world.rand.nextDouble(), (double)(y - 2) + world.rand.nextDouble() * 3.9D, (double)z + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    world.notifyBlockChange(x + l, y, z, getBlockById(0));
                    world.notifyBlockChange(x + l + 1, y, z, getBlockById(0));
                    world.notifyBlockChange(x + l + 2, y, z, getBlockById(0));
                    world.notifyBlockChange(x + l, y - 1, z, getBlockById(0));
                    world.notifyBlockChange(x + l + 1, y - 1, z, getBlockById(0));
                    world.notifyBlockChange(x + l + 2, y - 1, z, getBlockById(0));
                    world.notifyBlockChange(x + l + 1, y - 2, z, getBlockById(0));
                    return;
                }
            }
        }
    }
    
    @Overwrite
    private boolean func_149966_a(World world, int x, int y, int z, int skullType)
    {
        if (world.getBlock(x, y, z) != this)
        {
            return false;
        }
        
        else
        {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            
            return tileEntity != null && tileEntity instanceof TileEntitySkull ? ((TileEntitySkull) tileEntity).func_145904_a() == skullType : false;
        }
    }
    
    private boolean isSoulSandOrSoil(World world, int x, int y, int z)
    {
        if (Constants.V2)
        {
            return world.getBlock(x, y, z) == Blocks.soul_sand || world.getBlock(x, y, z) == RegistryBlocks.soul_soil;
        }
        
        return world.getBlock(x, y, z) == Blocks.soul_sand;
    }
}