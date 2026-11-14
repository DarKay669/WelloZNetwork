package fr.welloznetwork.wellozmod.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryCreativeTabs;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenAzaleaTree;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenCherryTree;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenFloweringAzaleaTree;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenMangroveTree;
import fr.welloznetwork.wellozmod.world.gen.feature.WorldGenWinterTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

@Custom(BlockSapling.class)
public class CustomBlockSapling extends BlockSapling
{
    private static final int CHERRY = 0, MANGROVE = 1, WINTER = 2, AZALEA = 3, FLOWERING_AZALEA = 4;
    int type;
    
    public CustomBlockSapling(String unlocalizedName, int type)
    {
        super();
        setBlockName(unlocalizedName);
        setBlockTextureName("wellozmod:" + unlocalizedName);
        setBlockBounds(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F);
        setCreativeTab(RegistryCreativeTabs.TAB_WELLOZ);
        this.type = type;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return this.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iiconRegister) 
    {
        this.blockIcon = iiconRegister.registerIcon(this.getTextureName());
    }
    
    public void func_149878_d(World world, int x, int y, int z, Random random)
    {
        world.setBlockToAir(x, y, z);
        Object object = null;
        
        switch (this.type)
        {
            case 0:
                object = new WorldGenCherryTree(true);
                
                if (!((WorldGenerator) (object)).generate(world, random, x, y, z))
                {
                    world.setBlock(x, y, z, this, world.getBlockMetadata(x, y, z) & 3, 3);
                }
                
                break;
                
            case 1:
                object = new WorldGenMangroveTree(true);
                
                if (!((WorldGenerator) (object)).generate(world, random, x, y, z))
                {
                    world.setBlock(x, y, z, this, world.getBlockMetadata(x, y, z) & 3, 3);
                }
                
                break;
                
            case 2:
                object = new WorldGenWinterTree(true);
                
                if (!((WorldGenerator) (object)).generate(world, random, x, y, z))
                {
                    world.setBlock(x, y, z, this, world.getBlockMetadata(x, y, z) & 3, 3);
                }
                
                break;
                
            case 3:
                object = new WorldGenAzaleaTree(true);
                
                if (!((WorldGenerator) (object)).generate(world, random, x, y, z))
                {
                    world.setBlock(x, y, z, this, world.getBlockMetadata(x, y, z) & 3, 3);
                }
                
                break;
                
            case 4:
                object = new WorldGenFloweringAzaleaTree(true);
                
                if (!((WorldGenerator) (object)).generate(world, random, x, y, z))
                {
                    world.setBlock(x, y, z, this, world.getBlockMetadata(x, y, z) & 3, 3);
                }
        }
    }
    
    public void getSubBlocks(Item item, CreativeTabs createiveTabs, List list)
    {
        list.add(new ItemStack(item, 1, 0));
    }
    
    protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.netherrack || block == Blocks.end_stone;
    }
}