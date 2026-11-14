package fr.welloznetwork.wellozmod.client.cosmetic.emote.emotes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Tween;
import fr.welloznetwork.wellozmod.client.cosmetic.emote.AEmote;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class EmoteWave extends AEmote
{
    public EmoteWave(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        super(entityPlayer, modelBiped, armorModel, armorLegsModel);
    }
    
    public Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped)
    {
        return Timeline.createSequence().push(Tween.to(modelBiped, 6, 200.0F).target(-2.8274333F)).push(Tween.to(modelBiped, 8, 300.0F).target(-0.9424779F).repeatYoyo(5, 0.0F)).push(Tween.to(modelBiped, 6, 200.0F).target(0.0F));
    }
    
    public boolean usesBodyPart(int part)
    {
        return (part == 6);
    }
}