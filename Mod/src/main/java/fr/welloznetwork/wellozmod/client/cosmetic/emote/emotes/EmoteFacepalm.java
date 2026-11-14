package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteFacepalm extends AEmote
{
    public EmoteFacepalm(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 0, 1000.0F).target(0.2F)).push(Tween.to(modelBiped, 1, 1000.0F).target(0.0F)).push(Tween.to(modelBiped, 2, 1000.0F).target(0.0F)).push(Tween.to(modelBiped, 9, 400.0F).target(-2.3415928F)).push(Tween.to(modelBiped, 11, 400.0F).target(-1.0F)).end().push(Tween.to(modelBiped, 1, 300.0F).target(0.05F)).push(Tween.to(modelBiped, 1, 300.0F).target(-0.05F).repeatYoyo(4, 0.0F)).push(Tween.to(modelBiped, 1, 300.0F).target(0.0F)).beginParallel().push(Tween.to(modelBiped, 0, 500.0F).target(0.0F)).push(Tween.to(modelBiped, 9, 600.0F).target(0.0F)).push(Tween.to(modelBiped, 11, 600.0F).target(0.0F)).end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 0 || part == 9);
    }
}