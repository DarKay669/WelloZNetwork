package fr.welloznetwork.wellozmod.mixins.block;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.world.World;

@Mixin(BlockDeadBush.class)
public class MixinBlockDeadBush extends BlockBush
{
    @Overwrite
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return Items.stick;
    }
    
    public int quantityDropped(Random random)
    {
        return random.nextInt(3);
    }
    
    public void harvestBlock(World world, EntityPlayer entityPlayer, int x, int y, int z, int metadata)
    {
        if (!(entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() instanceof ItemShears))
        {
            super.harvestBlock(world, entityPlayer, x, y, z, metadata);
        }
    }
}