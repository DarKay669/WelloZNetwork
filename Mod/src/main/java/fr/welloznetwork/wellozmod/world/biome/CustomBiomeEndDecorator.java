package fr.welloznetwork.wellozmod.world.biome;

import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenSpikes;

@Custom(BiomeEndDecorator.class)
public class CustomBiomeEndDecorator extends BiomeEndDecorator
{
    public CustomBiomeEndDecorator()
    {
        this.spikeGen = new WorldGenSpikes(Blocks.end_stone);
    }
    
    protected void genDecorations(BiomeGenBase biomeGenBase)
    {
        this.generateOres();
        
        if (Math.abs(this.chunk_X) < 256 && Math.abs(this.chunk_Z) < 256 && this.randomGenerator.nextInt(5) == 0)
        {
            int x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            int y = this.currentWorld.getTopSolidOrLiquidBlock(x, z);
            
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, x, y, z);
        }
        
        if (this.chunk_X == 0 && this.chunk_Z == 0)
        {
            EntityDragon entityDragon = new EntityDragon(this.currentWorld);
            
            entityDragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
            
            this.currentWorld.spawnEntityInWorld(entityDragon);
        }
    }
}