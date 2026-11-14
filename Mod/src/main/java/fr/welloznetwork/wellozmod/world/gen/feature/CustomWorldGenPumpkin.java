package fr.welloznetwork.wellozmod.world.gen.feature;

import java.util.Random;

import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenerator;

@Custom(WorldGenPumpkin.class)
public class CustomWorldGenPumpkin extends WorldGenerator
{
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        for (int i = 0; i < 64; i++)
        {
            int posX = x + random.nextInt(8) - random.nextInt(8);
            int posY = y + random.nextInt(4) - random.nextInt(4);
            int posZ = z + random.nextInt(8) - random.nextInt(8);
            
            if (world.isAirBlock(posX, posY, posZ) && world.getBlock(posX, posY - 1, posZ) == Blocks.grass && RegistryBlocks.pumpkin_face_1_off.canPlaceBlockAt(world, posX, posY, posZ))
            {
                world.setBlock(posX, posY, posZ, RegistryBlocks.pumpkin_face_1_off, random.nextInt(4), 2);
            }
            
            else if (world.isAirBlock(posX, posY, posZ) && world.getBlock(posX, posY - 1, posZ) == Blocks.grass && RegistryBlocks.pumpkin_face_2_off.canPlaceBlockAt(world, posX, posY, posZ))
            {
                world.setBlock(posX, posY, posZ, RegistryBlocks.pumpkin_face_2_off, random.nextInt(4), 2);
            }
            
            else if (world.isAirBlock(posX, posY, posZ) && world.getBlock(posX, posY - 1, posZ) == Blocks.grass && RegistryBlocks.pumpkin_face_3_off.canPlaceBlockAt(world, posX, posY, posZ))
            {
                world.setBlock(posX, posY, posZ, RegistryBlocks.pumpkin_face_3_off, random.nextInt(4), 2);
            }
            
            else if (world.isAirBlock(posX, posY, posZ) && world.getBlock(posX, posY - 1, posZ) == Blocks.grass && RegistryBlocks.pumpkin_face_4_off.canPlaceBlockAt(world, posX, posY, posZ))
            {
                world.setBlock(posX, posY, posZ, RegistryBlocks.pumpkin_face_4_off, random.nextInt(4), 2);
            }
            
            else if (world.isAirBlock(posX, posY, posZ) && world.getBlock(posX, posY - 1, posZ) == Blocks.grass && RegistryBlocks.pumpkin_face_5_off.canPlaceBlockAt(world, posX, posY, posZ))
            {
                world.setBlock(posX, posY, posZ, RegistryBlocks.pumpkin_face_5_off, random.nextInt(4), 2);
            }
        }
        
        return true;
    }
}