package fr.welloznetwork.wellozmod.world.gen.structure;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class MapGenMesaMineshaft extends MapGenMineshaft
{
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        if (ArrayUtils.contains(BiomeDictionary.getTypesForBiome(this.worldObj.getBiomeGenForCoords((chunkX << 4) + 8, (chunkZ << 4) + 8)), Type.MESA))
        {
            return new StructureMesaMineshaftStart(this.worldObj, this.rand, chunkX, chunkZ);
        }
        
        return new StructureMineshaftStart(this.worldObj, this.rand, chunkX, chunkZ);
    }
}