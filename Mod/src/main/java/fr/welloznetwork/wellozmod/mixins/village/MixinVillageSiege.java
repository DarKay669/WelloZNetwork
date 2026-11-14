package fr.welloznetwork.wellozmod.mixins.village;

import java.util.Iterator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.World;

@Mixin(VillageSiege.class)
public abstract class MixinVillageSiege
{
    @Shadow
    private World worldObj;
    
    @Shadow
    private int field_75533_d;
    
    @Shadow
    private int field_75534_e;
    
    @Shadow
    private Village theVillage;
    
    @Shadow
    private int field_75532_g;
    
    @Shadow
    private int field_75538_h;
    
    @Shadow
    private int field_75539_i;
    
    @Overwrite
    private boolean func_75529_b()
    {
        List<EntityPlayer> list = this.worldObj.playerEntities;
        Iterator<EntityPlayer> iterator = list.iterator();
        
        while (true)
        {
            if (!iterator.hasNext())
            {
                return false;
            }
            
            EntityPlayer entityPlayer = iterator.next();
            
            this.theVillage = this.worldObj.villageCollectionObj.findNearestVillage((int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ, 1);
            
            if (this.theVillage != null && this.theVillage.getNumVillageDoors() >= 10 && this.theVillage.getTicksSinceLastDoorAdding() >= 20 && this.theVillage.getNumVillagers() >= 20)
            {                
                boolean flag = false;
                
                for (int i = 0; i < 10; i++)
                {
                    this.field_75532_g = this.theVillage.getCenter().posX + (int) ((double) (MathHelper.cos(this.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F) * (float) this.theVillage.getVillageRadius()) * 0.9D);
                    this.field_75538_h = this.theVillage.getCenter().posY;
                    this.field_75539_i = this.theVillage.getCenter().posZ + (int) ((double) (MathHelper.sin(this.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F) * (float) this.theVillage.getVillageRadius()) * 0.9D);
                    
                    flag = false;
                    
                    Iterator<Village> iterator1 = this.worldObj.villageCollectionObj.getVillageList().iterator();
                    
                    while (iterator1.hasNext())
                    {
                        Village village = iterator1.next();
                        
                        if (village != this.theVillage && village.isInRange(this.field_75532_g, this.field_75538_h, this.field_75539_i))
                        {
                            flag = true;
                            break;
                        }
                    }
                    
                    if (!flag)
                    {
                        break;
                    }
                }
                
                if (flag)
                {
                    return false;
                }
                
                if (this.func_75527_a(this.field_75532_g, this.field_75538_h, this.field_75539_i) != null)
                {
                    break;
                }
            }
        }
        
        this.field_75534_e = 0;
        this.field_75533_d = 20;
        
        return true;
    }
    
    @Shadow
    public abstract Vec3 func_75527_a(int x, int y, int z);
}