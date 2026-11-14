package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteThink extends AEmote
{
    public EmoteThink(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 0, 150.0F).target(0.0F)).push(Tween.to(modelBiped, 1, 150.0F).target(0.0F)).push(Tween.to(modelBiped, 2, 150.0F).target(0.0F)).push(Tween.to(modelBiped, 6, 400.0F).target(-2.3415928F)).push(Tween.to(modelBiped, 8, 400.0F).target(0.4F)).end().push(Tween.to(modelBiped, 8, 150.0F).target(0.3F).repeatYoyo(5, 0.0F)).beginParallel().push(Tween.to(modelBiped, 6, 300.0F).target(0.0F)).push(Tween.to(modelBiped, 8, 300.0F).target(0.0F)).end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 0 || part == 6);
    }
}