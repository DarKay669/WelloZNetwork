package fr.welloznetwork.wellozmod.client.cosmetic.emote;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.BaseTween;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.Timeline;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.TweenCallback;
import fr.welloznetwork.venus.lib.api.aurelienribon.tweenengine.TweenManager;
import fr.welloznetwork.wellozmod.client.listener.ClientListener;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public abstract class AEmote
{
    public TweenManager tweenManager;
    public ModelBiped model, armorModel, armorLegsModel;
    private State state;
    private boolean done = false;
    
    public abstract Timeline getTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped);
    
    public abstract boolean usesBodyPart(int part);
    
    public AEmote(EntityPlayer entityPlayer, ModelBiped modelBiped, ModelBiped armorModel, ModelBiped armorLegsModel)
    {
        this.tweenManager = new TweenManager();
        this.state = new State(this);
        this.model = modelBiped;
        this.armorModel = armorModel;
        this.armorLegsModel = armorLegsModel;
        
        this.startTimeline(entityPlayer, modelBiped, true);
        this.startTimeline(entityPlayer, armorModel, false);
        this.startTimeline(entityPlayer, armorLegsModel, false);
    }
    
    public void update(boolean update)
    {
        this.state.load(this.model);
        this.state.load(this.armorModel);
        this.state.load(this.armorLegsModel);
        
        if (update)
        {
            tweenManager.update(ClientListener.delta);
            this.state.save(this.model);
        }
    }
    
    private void startTimeline(EntityPlayer entityPlayer, ModelBiped modelBiped, boolean callback)
    {
        Timeline timeline = this.getTimeline(entityPlayer, modelBiped).start(this.tweenManager);
        
        if (callback)
        {
            timeline.setCallback(new FinishCallback());
        }
    }
    
    public boolean isDone()
    {
        return this.done;
    }
    
    private class FinishCallback implements TweenCallback
    {
        private FinishCallback() {}
        
        public void onEvent(int type, BaseTween<?> source)
        {
            if (type == 8)
            {
                AEmote.this.done = true;
            }
        }
    }
    
    private class State
    {
        float[] states = new float[0];
        
        AEmote emote;
        
        public State(AEmote emote)
        {
            this.emote = emote;
        }
        
        public void save(ModelBiped modelBiped)
        {
            float[] values = new float[1];
            
            for (int i = 0; i < 20; i++)
            {
                ModelAccessor.INSTANCE.getValues(modelBiped, i, values);
                
                this.states[i] = values[0];
            }
        }
        
        public void load(ModelBiped modelBiped)
        {
            if (this.states.length == 0)
            {
                this.states = new float[20];
                return;
            }
            
            float[] values = new float[1];
            
            for (int i = 0; i < 20; i++)
            {
                values[0] = this.states[i];
                
                int part = i / 3 * 3;
                
                if (this.emote.usesBodyPart(part))
                {
                    ModelAccessor.INSTANCE.setValues(modelBiped, i, values);
                }
            }
        }
    }
}