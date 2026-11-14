package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteZombie extends AEmote
{
    public EmoteZombie(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createParallel().beginSequence().push(Tween.set(modelBiped, 0).target(0.0F)).push(Tween.set(modelBiped, 1).target(0.0F)).push(Tween.to(modelBiped, 2, 1000.0F).target(0.5235988F)).push(Tween.to(modelBiped, 2, 2000.0F).target(-0.5235988F).repeatYoyo(3, 0.0F)).push(Tween.to(modelBiped, 2, 500.0F).target(0.0F)).end().beginSequence().beginParallel().push(Tween.to(modelBiped, 9, 1000.0F).target(-1.2707963F)).push(Tween.to(modelBiped, 6, 1000.0F).target(-1.8707964F)).end().beginParallel().push(Tween.to(modelBiped, 9, 1000.0F).target(-1.8707964F).repeatYoyo(7, 0.0F)).push(Tween.to(modelBiped, 6, 1000.0F).target(-1.2707963F).repeatYoyo(7, 0.0F)).end().beginParallel().push(Tween.to(modelBiped, 9, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 6, 500.0F).target(0.0F)).end().end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 0 || part == 9 || part == 6);
    }
}