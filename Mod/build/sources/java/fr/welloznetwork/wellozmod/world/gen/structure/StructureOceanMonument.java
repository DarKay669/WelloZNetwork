package fr.welloznetwork.wellozmod.world.gen.structure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import fr.welloznetwork.wellozmod.WelloZMod;
import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class StructureOceanMonument
{
    private static final List<BiomeGenBase> VALID_BIOMES = Arrays.asList(BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver);
    private static final Map<WorldCoord, Integer> MAP = new HashMap<WorldCoord, Integer>();
    
    public static void init()
    {
        try
        {
            InputStream inputStream = WelloZMod.class.getResourceAsStream("/assets/ocean_monument.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            
            while ((s = bufferedReader.readLine()) != null)
            {
                String[] data = s.split("-");
                data[0] = data[0].trim();
                data[0] = data[0].substring(1, data[0].length() - 1);
                data[1] = data[1].trim();
                
                String[] coords = data[0].split(",");
                                
                MAP.put(new WorldCoord(Integer.parseInt(coords[0].trim()), Integer.parseInt(coords[1].trim()), Integer.parseInt(coords[2].trim())), Integer.parseInt(data[1]));
            }
            
            bufferedReader.close();
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static Map<WorldCoord, Integer> getMap()
    {
        return MAP;
    }
    
    public static void buildTemple(World world, int x, int y, int z)
    {
        if (world.isRemote)
        {
            return;
        }
        
        for (Entry<WorldCoord, Integer> entry : StructureOceanMonument.getMap().entrySet())
        {
            WorldCoord worldCoord = entry.getKey();
            int value = entry.getValue(), metadata = 0;
            Block block = null;
            
            switch (value)
            {
                case 0:
                case 1:
                case 2:
                    block = RegistryBlocks.prismarine_bricks;
                    metadata = value;
                    break;
                    
                case 3:
                    block = RegistryBlocks.sea_lantern;
                    break;
                    
                case 4:
                    block = Blocks.gold_block;
                    break;
                    
                case 5:
                    block = Blocks.sponge;
                    metadata = 1;
                    break;
                    
                case 6:
                    block = Blocks.water;
                    break;
            }
            
            if (block != null)
            {
                world.setBlock(worldCoord.x + x, worldCoord.y + y, worldCoord.z + z, block, metadata, 2);
            }
        }
        
        for (int i = 0; i < 7; i++)
        {
            generatePillar(world, x + 5 * i + 4 * i, y, z, RegistryBlocks.prismarine_bricks, 1);
            generatePillar(world, x, y, z + 5 * i + 4 * i, RegistryBlocks.prismarine_bricks, 1);
            generatePillar(world, x + 54, y, z + 5 * i + 4 * i, RegistryBlocks.prismarine_bricks, 1);
            
            if (i != 3)
            {
                generatePillar(world, x + 5 * i + 4 * i, y, z + 54, RegistryBlocks.prismarine_bricks, 1);
            }
        }
    }
    
    private static void generatePillar(World world, int x, int y, int z, Block block, int metadata)
    {
        for (int i = 1; i <= 5; i++)
        {
            generatePillarSection(world, x, y - i, z, block, metadata);
        }
        
        y -= 5;
        
        for (; y >= 0; y--)
        {
            generatePillarSection(world, x, y, z, block, metadata);
            
            for (int i = 0; i < 4; i++)
            {
                for (int k = 0; k < 4; k++)
                {
                    if (world.getBlock(x + i, y, z).getMaterial() != Material.water && y > 3)
                    {
                        generatePillarSection(world, x, y - 1, z, block, metadata);
                        generatePillarSection(world, x, y - 2, z, block, metadata);
                        return;
                    }
                }
            }
        }
    }
    
    private static void generatePillarSection(World world, int x, int y, int z, Block block, int metadata)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int k = 0; k < 4; k++)
            {
                if (world.getBlock(x + i, y, z).getBlockHardness(world, x + i, y, z + k) > 0)
                {
                    world.setBlock(x + i, y, z + k, block, metadata, 2);
                }
            }
        }
    }
    
    public static void generateFile(World world, int x, int y, int z, String path)
    {
        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path)));
            
            for (int i = 0; i < 58; i++)
            {
                for (int j = 0; j < 31 - 9; j++)
                {
                    for (int k = 0; k < 58; k++)
                    {
                        Block block = world.getBlock(x + i, y + j, z + k);
                        int metadata = world.getBlockMetadata(x + i, y + j, z + k);
                        
                        String s = "(" + i + ", " + j + ", " + k + ") - ";
                        
                        if (block == RegistryBlocks.prismarine_bricks)
                        {
                            s += metadata;
                        }
                        
                        else if (block == RegistryBlocks.sea_lantern)
                        {
                            s += 3;
                        }
                        
                        else if (block == Blocks.gold_block)
                        {
                            s += 4;
                        }
                        
                        else if (block == Blocks.sponge)
                        {
                            s += 5;
                        }
                        
                        else if (block == Blocks.stained_glass)
                        {
                            s += 6;
                        }
                        
                        else
                        {
                            s = null;
                        }
                        
                        if (s != null)
                        {
                            bufferedWriter.write(s);
                            bufferedWriter.newLine();
                        }
                    }
                }
            }
            
            bufferedWriter.close();
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static boolean canSpawnAt(World world, int chunkX, int chunkZ)
    {
        int xx = chunkX;
        int zz = chunkZ;
        
        if (chunkX < 0)
        {
            chunkX -= 32 - 1;
        }
        
        if (chunkZ < 0)
        {
            chunkZ -= 32 - 1;
        }
        
        int i1 = chunkX / 32;
        int j1 = chunkZ / 32;
        
        Random random = world.setRandomSeed(i1, j1, 10387313);
        
        i1 *= 32;
        j1 *= 32;
        i1 += (random.nextInt(32 - 5) + random.nextInt(32 - 5)) / 2;
        j1 += (random.nextInt(32 - 5) + random.nextInt(32 - 5)) / 2;
        
        if (xx == i1 && zz == j1)
        {
            if (world.getWorldChunkManager().getBiomeGenAt(xx * 16 + 8, zz * 16 + 8) != BiomeGenBase.deepOcean)
            {
                return false;
            }
            
            return world.getWorldChunkManager().areBiomesViable(xx * 16 + 8, zz * 16 + 8, 29, VALID_BIOMES);
        }
        
        return false;
    }
    
    private static class WorldCoord implements Comparable<WorldCoord>
    {
        public int x, y, z;
        
        public WorldCoord(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public WorldCoord(TileEntity tileEntity)
        {
            this.x = tileEntity.xCoord;
            this.y = tileEntity.yCoord;
            this.z = tileEntity.zCoord;
        }
        
        public WorldCoord add(ForgeDirection forgeDirection)
        {
            return new WorldCoord(this.x + forgeDirection.offsetX, this.y + forgeDirection.offsetY, this.z + forgeDirection.offsetZ);
        }
        
        public int hashCode()
        {
            return new HashCodeBuilder().append(x).append(y).append(z).hashCode();
        }
        
        public int compareTo(WorldCoord worldCoord)
        {
            return Integer.compare(this.x * this.x + this.y * this.y + this.z * this.z, worldCoord.x * worldCoord.x + worldCoord.y * worldCoord.y + worldCoord.z * worldCoord.z);
        }
        
        public boolean equals(Object o)
        {
            if (!(o instanceof WorldCoord))
            {
                return false;
            }
            
            WorldCoord worldCoord = (WorldCoord) o;
            
            return this.x == worldCoord.x && this.y == worldCoord.y && this.z == worldCoord.z;
        }
        
        public String toString()
        {
            return "Coord: " + this.x + ", " + this.y + ", " + this.z;
        }
    }
}