package fr.welloznetwork.wellozmod.mixins.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import fr.welloznetwork.wellozmod.init.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

@Mixin(Explosion.class)
public class MixinExplosion
{
    @Shadow
    public boolean isFlaming;
    
    @Shadow
    public boolean isSmoking = true;
    
    @Shadow
    private Random explosionRNG = new Random();
    
    @Shadow
    private World worldObj;
    
    @Shadow
    public double explosionX;
    
    @Shadow
    public double explosionY;
    
    @Shadow
    public double explosionZ;
    
    @Shadow
    public float explosionSize;
    
    @Shadow
    public List affectedBlockPositions = new ArrayList();
    
    @Overwrite
    public void doExplosionB(boolean spawnParticles)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        
        if (this.explosionSize >= 2.0F && this.isSmoking)
        {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }
        
        else
        {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }
        
        Iterator iterator;
        ChunkPosition chunkPosition;
        int x, y, z;
        Block block;
        
        if (this.isSmoking)
        {
            iterator = this.affectedBlockPositions.iterator();
            
            while (iterator.hasNext())
            {
                chunkPosition = (ChunkPosition) iterator.next();
                x = chunkPosition.chunkPosX;
                y = chunkPosition.chunkPosY;
                z = chunkPosition.chunkPosZ;
                block = this.worldObj.getBlock(x, y, z);
                
                if (spawnParticles)
                {
                    double d0 = (double) ((float) x + this.worldObj.rand.nextFloat());
                    double d1 = (double) ((float) y + this.worldObj.rand.nextFloat());
                    double d2 = (double) ((float) z + this.worldObj.rand.nextFloat());
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = (double) MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                    
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    
                    double d7 = 0.5D / (d6 / (double) this.explosionSize + 0.1D);
                    
                    d7 *= (double) (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat());
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    
                    this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
                    this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
                }
                
                if (block.getMaterial() != Material.air)
                {
                    if (block.canDropFromExplosion((Explosion) (Object) this))
                    {
                        block.dropBlockAsItemWithChance(this.worldObj, x, y, z, this.worldObj.getBlockMetadata(x, y, z), 1.0F / this.explosionSize, 0);
                    }
                    
                    block.onBlockExploded(this.worldObj, x, y, z, (Explosion) (Object) this);
                }
            }
        }
        
        if (this.isFlaming)
        {
            iterator = this.affectedBlockPositions.iterator();
            
            while (iterator.hasNext())
            {
                chunkPosition = (ChunkPosition) iterator.next();
                x = chunkPosition.chunkPosX;
                y = chunkPosition.chunkPosY;
                z = chunkPosition.chunkPosZ;
                block = this.worldObj.getBlock(x, y, z);
                Block block1 = this.worldObj.getBlock(x, y - 1, z);
                
                if (!(this.worldObj.provider.dimensionId == -2))
                {
                    if (block.getMaterial() == Material.air && block1.func_149730_j() && this.explosionRNG.nextInt(3) == 0)
                    {
                        this.worldObj.setBlock(x, y, z, Blocks.fire);
                    }
                }
                
                else if (block.getMaterial() == Material.air && block1.func_149730_j() && this.explosionRNG.nextInt(3) == 0)
                {
                    this.worldObj.setBlock(x, y, z, RegistryBlocks.soul_fire);
                }
            }
        }
    }
}