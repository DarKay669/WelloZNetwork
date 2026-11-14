package fr.welloznetwork.wellozmod.world.gen.structure;

import java.util.LinkedList;
import java.util.Random;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureMineshaftStart;

public class StructureMesaMineshaftStart extends StructureMineshaftStart
{
    public StructureMesaMineshaftStart() {}
    
    public StructureMesaMineshaftStart(World world, Random random, int chunkX, int chunkZ)
    {
        StructureMineshaftStart structureMineshaftStart = new StructureMineshaftStart();
        ObfuscationReflectionHelper.setPrivateValue(StructureMineshaftStart.class, structureMineshaftStart, chunkX, "field_143024_c");
        ObfuscationReflectionHelper.setPrivateValue(StructureMineshaftStart.class, structureMineshaftStart, chunkZ, "field_143023_d");
        StructureMesaMineshaftPieces.MesaRoom room = new StructureMesaMineshaftPieces.MesaRoom(0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
        this.components.add(room);
        room.buildComponent(room, this.components, random);
        this.updateBoundingBox();
        int j = 63 - this.boundingBox.maxY + this.boundingBox.getYSize() / 2 - -5;
        this.boundingBox.offset(0, j, 0);
        
        for (StructureComponent structureComponent : (LinkedList<StructureComponent>) this.components)
        {
            if (structureComponent instanceof StructureMesaMineshaftPieces.Piece)
            {
                ((StructureMesaMineshaftPieces.Piece) structureComponent).offset(0, j, 0);
            }
        }
    }
}