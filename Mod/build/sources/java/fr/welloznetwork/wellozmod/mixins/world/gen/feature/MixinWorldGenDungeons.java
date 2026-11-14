package fr.welloznetwork.wellozmod.mixins.world.gen.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDungeons;

@Mixin(WorldGenDungeons.class)
public abstract class MixinWorldGenDungeons
{
    private static final List<ChestContentFiller> FILLERS = new ArrayList<>();
    private static final List<MobSpawnerSetter> SETTERS = new ArrayList<>();
    
    @Inject(method = "generate", at = @At("RETURN"))
    private void newGenerate(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> callbackInfoReturnable)
    {
        if (!callbackInfoReturnable.getReturnValue())
        {
            return;
        }
        
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        
        if (tileEntity == null)
        {
            return;
        }
        
        if (!(tileEntity instanceof TileEntityChest))
        {
            this.fillChest(tileEntity, random);
        }
        
        if (!(tileEntity instanceof TileEntityMobSpawner))
        {
            this.handleSpawner(tileEntity, this.pickMobSpawner(random));
        }
    }
    
    @Shadow
    public abstract String pickMobSpawner(Random random);
    
    private void addFiller(ChestContentFiller chestContentFiller)
    {
        if (chestContentFiller == null)
        {
            throw new IllegalArgumentException("Tried to add null ChestContentFiller");
        }
        
        FILLERS.add(chestContentFiller);
    }
    
    private void addSetter(MobSpawnerSetter mobSpawnerSetter)
    {
        if (mobSpawnerSetter == null)
        {
            throw new IllegalArgumentException("Tried to add null MobSpawnerSetter");
        }
        
        SETTERS.add(mobSpawnerSetter);
    }
    
    private void fillChest(TileEntity tileEntity, Random random)
    {
        for (ChestContentFiller chestContentFiller : FILLERS)
        {
            chestContentFiller.fillChest(tileEntity, random);
        }
    }
    
    private boolean handleSpawner(TileEntity tileEntity, String entityName)
    {
        boolean handled = false;
        
        for (MobSpawnerSetter mobSpawnerSetter : SETTERS)
        {
            handled |= mobSpawnerSetter.handleSpawner(tileEntity, entityName);
        }
        
        return handled;
    }
    
    private interface ChestContentFiller
    {
        void fillChest(TileEntity tileEntity, Random random);
    }
    
    private interface MobSpawnerSetter
    {
        boolean handleSpawner(TileEntity tileEntity, String entityName);
    }
}