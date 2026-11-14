package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteGangnamStyle extends AEmote
{
    public EmoteGangnamStyle(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().beginParallel().push(Tween.to(modelBiped, 6, 400.0F).target(-1.3707963F)).push(Tween.to(modelBiped, 9, 400.0F).target(-1.7707964F)).push(Tween.to(modelBiped, 7, 400.0F).target(-0.63539815F)).push(Tween.to(modelBiped, 10, 400.0F).target(0.63539815F)).push(Tween.to(modelBiped, 17, 200.0F).target(0.5235988F)).push(Tween.to(modelBiped, 20, 200.0F).target(-0.5235988F)).end().beginParallel().push(Tween.to(modelBiped, 6, 200.0F).target(-0.77079636F).repeatYoyo(16, 0.0F)).push(Tween.to(modelBiped, 9, 200.0F).target(-1.1707964F).repeatYoyo(16, 0.0F)).push(Tween.to(modelBiped, 17, 200.0F).target(0.92359877F).repeatYoyo(8, 200.0F)).push((Tween.to(modelBiped, 20, 200.0F).delay(200.0F)).target(-0.92359877F).repeatYoyo(8, 200.0F)).end().beginParallel().push(Tween.to(modelBiped, 6, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 9, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 7, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 10, 400.0F).target(0.0F)).push(Tween.to(modelBiped, 17, 200.0F).target(0.0F)).push(Tween.to(modelBiped, 20, 200.0F).target(0.0F)).end();
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 9 || part == 6 || part == 18 || part == 15);
    }
}