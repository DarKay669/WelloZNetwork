package fr.welloznetwork.wellozmod.block;

import java.util.Arrays;
import java.util.List;

import fr.welloznetwork.wellozmod.entity.block.EntityTNTPresent;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistryItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPresent extends Block
{
    private static final List<ItemStack> PRESENTS = Arrays.asList(new ItemStack(RegistryItems.candy_cane), new ItemStack(RegistryItems.candy_cane_axe), new ItemStack(RegistryItems.candy_cane_sword));
    
	public BlockPresent(String unlocalizedName)
	{
		super(Material.glass);
		setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.35F, 0.75F);
		setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ_CHRISTMAS);
		setBlockName(unlocalizedName);
		setBlockTextureName("wellozmod:" + unlocalizedName);
		setHardness(0.6F);
		setLightLevel(1.0F);
		setStepSound(soundTypeWood);
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public void harvestBlock(World world, EntityPlayer entityPlayer, int x, int y, int z, int metadata)
	{
	    if (world.rand.nextInt(4) == 0)
        {
            for (int size = 1; size <= (int) (((9 - 6 + 1) * world.rand.nextDouble()) + 6); size++)
            {
                if (!world.isRemote)
                {
                    world.spawnEntityInWorld(new EntityXPOrb(world, x, y, z, size));
                }
            }
        }
        
        else if (world.rand.nextInt(4) == 1)
        {
            if (world.rand.nextInt(9) == 0)
            {
                this.dropBlockAsItem(world, x, y, z, PRESENTS.get(world.rand.nextInt(PRESENTS.size())));
            }
            
            else
            {
                for (int size = 1; size <= (int) (((9 - 6 + 1) * world.rand.nextDouble()) + 6); size++)
                {
                    this.dropBlockAsItem(world, x, y, z, new ItemStack(RegistryItems.gingerbread_man));
                }
            }
        }
	    
        else
        {
            EntityTNTPresent entityTNTPresent = new EntityTNTPresent(world, x, y, z);
            
            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityTNTPresent);
            }
            
            world.playSoundAtEntity(entityTNTPresent, "game.tnt.primed", 1.0F, 1.0F);
        }
	}
}