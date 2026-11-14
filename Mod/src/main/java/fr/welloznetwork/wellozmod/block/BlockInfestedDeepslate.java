package fr.welloznetwork.wellozmod.block;

import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.world.World;

public class BlockInfestedDeepslate extends Block
{
    public BlockInfestedDeepslate()
    {
        super(Material.rock);
        setResistance(0.75F);
        setHardness(1.5F);
        setHarvestLevel("pickaxe", 0);
        setBlockName("infested_deepslate");
        setBlockTextureName("wellozmod:deepslate");
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        setStepSound(RegistrySounds.SOUND_TYPE_DEEPSLATE);
    }
    
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i)
    {
        if (!world.isRemote)
        {
            EntitySilverfish entitySilverFish = new EntitySilverfish(world);
            entitySilverFish.setLocationAndAngles((double) x + 0.5D, (double) y, (double) z + 0.5D, 0.0F, 0.0F);
            world.spawnEntityInWorld(entitySilverFish);
            entitySilverFish.spawnExplosionParticle();
        }
        
        super.onBlockDestroyedByPlayer(world, x, y, z, i);
    }
}