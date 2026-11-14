package fr.welloznetwork.wellozmod.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.entity.player.IEntityPlayer;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ElytraSound extends MovingSound
{
    private final EntityPlayer entityPlayer;
    private int time;
    
    public ElytraSound(EntityPlayer entityPlayer)
    {
        super(new ResourceLocation(RegistrySounds.SOUND_TYPE_ELYTRA_FLYING));
        this.entityPlayer = entityPlayer;
        this.repeat = true;
        this.field_147665_h = 0;
        this.volume = 0.1F;
    }
    
    public void update()
    {
        this.time++;
        
        if (!this.entityPlayer.isDead && (this.time <= 20 || ((IEntityPlayer) this.entityPlayer).isElytraFlying()))
        {
            this.xPosF = (float) this.entityPlayer.posX;
            this.yPosF = (float) this.entityPlayer.posY;
            this.zPosF = (float) this.entityPlayer.posZ;
            
            float f = MathHelper.sqrt_double(this.entityPlayer.motionX * this.entityPlayer.motionX + this.entityPlayer.motionZ * this.entityPlayer.motionZ + this.entityPlayer.motionY * this.entityPlayer.motionY);
            float f1 = f / 2.0F;
            
            if ((double) f >= 0.01D)
            {
                this.volume = MathHelper.clamp_float(f1 * f1, 0.0F, 1.0F);
            }
            
            else
            {
                this.volume = 0.0F;
            }
            
            if (this.time < 20)
            {
                this.volume = 0.0F;
            }
            
            else if (this.time < 40)
            {
                this.volume = (float) ((double) this.volume * ((double) (this.time - 20) / 20.0D));
            }
            
            if (this.volume > 0.8F)
            {
                this.field_147663_c = 1.0F + (this.volume - 0.8F);
            }
            
            else
            {
                this.field_147663_c = 1.0F;
            }
        }
        
        else
        {
            this.donePlaying = true;
        }
    }
}