package fr.welloznetwork.wellozmod.mixins.block;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

@Mixin(BlockEnchantmentTable.class)
public abstract class MixinBlockEnchantmentTable extends BlockContainer
{
    protected MixinBlockEnchantmentTable(Material material)
    {
        super(material);
    }
    
    @Overwrite
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        for (int x1 = x - 2; x1 <= x + 2; x1++)
        {
            for (int z1 = z - 2; z1 <= z + 2; z1++)
            {
                if (x1 > x - 2 && x1 < x + 2 && z1 == z - 1)
                {
                    z1 = z + 2;
                }
                
                if (random.nextInt(16) == 0)
                {
                    for (int y1 = y; y1 <= y + 1; y1++)
                    {
                        if (world.getBlock(x1, y1, z1).getEnchantPowerBonus(world, x1, y1, z1) > 0.0F)
                        {
                            if (!world.isAirBlock((x1 - x) / 2 + x, y1, (z1 - z) / 2 + z))
                            {
                                break;
                            }
                            
                            world.spawnParticle("enchantmenttable", (double) x + 0.5D, (double) y + 2.0D, (double) z + 0.5D, (double) ((float) (x1 - x) + random.nextFloat()) - 0.5D, (double) ((float) (y1 - y) - random.nextFloat() - 1.0F), (double) ((float) (z1 - z) + random.nextFloat()) - 0.5D);
                        }
                    }
                }
            }
        }
    }
}