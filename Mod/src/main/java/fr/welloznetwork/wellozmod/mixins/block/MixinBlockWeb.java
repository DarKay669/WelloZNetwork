package fr.welloznetwork.wellozmod.mixins.block;

import java.util.ArrayList;
import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

@Mixin(BlockWeb.class)
public class MixinBlockWeb extends Block implements IShearable
{
    protected MixinBlockWeb(Material material)
    {
        super(material);
    }
    
    public boolean isShearable(ItemStack itemStack, IBlockAccess iBlockAccess, int x, int y, int z)
    {
        return true;
    }
    
    public ArrayList<ItemStack> onSheared(ItemStack itemStack, IBlockAccess iBlockAccess, int x, int y, int z, int fortune)
    {
        return new ArrayList<ItemStack> (Arrays.asList(new ItemStack(Blocks.web, 1, 0)));
    }
    
    public void harvestBlock(World world, EntityPlayer entityPlayer, int x, int y, int z, int metadata)
    {
        if (!(entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() instanceof ItemShears))
        {
            super.harvestBlock(world, entityPlayer, x, y, z, metadata);
        }
    }
}