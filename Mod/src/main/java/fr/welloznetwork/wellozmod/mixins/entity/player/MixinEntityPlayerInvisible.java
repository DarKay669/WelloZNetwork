package fr.welloznetwork.wellozmod.mixins.entity.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import fr.welloznetwork.wellozmod.command.CommandSpectator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.World;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayerInvisible extends EntityLivingBase
{
    public MixinEntityPlayerInvisible(World world)
    {
        super(world);
    }
    
    @Overwrite
    public boolean isInvisibleToPlayer(EntityPlayer entityPlayer)
    {
        if (!this.isInvisible())
        {
            return false;
        }
        
        else if (CommandSpectator.isSpectator())
        {
            return false;
        }
        
        else
        {
            Team team = this.getTeam();
            
            return team == null || entityPlayer == null || entityPlayer.getTeam() != team || !team.func_98297_h();
        }
    }
}