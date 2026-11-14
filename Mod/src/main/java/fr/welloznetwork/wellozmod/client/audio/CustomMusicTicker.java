package fr.welloznetwork.wellozmod.client.audio;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.init.RegistrySounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@Custom(MusicTicker.class)
@SideOnly(Side.CLIENT)
public class CustomMusicTicker implements IUpdatePlayerListBox
{
    private final Minecraft mc;
    private final Random random = new Random();
    private ISound currentMusic, currentRecord, menuMusic, minecraftMusic;
    private int timeUntilNextMusic = 100;
    
    public CustomMusicTicker(Minecraft mc)
    {
        this.mc = mc;
    }
    
    public void update()
    {
        MusicType musicType = this.getRandomTrack();
        
        if (this.mc.thePlayer != null)
        {
            if (this.mc.gameSettings.getSoundLevel(SoundCategory.MUSIC) == 0.0F)
            {
                if (this.currentMusic != null)
                {
                    this.stopMusic();
                    this.currentMusic = null;
                }
                
                return;
            }
            
            if (this.mc.thePlayer.dimension != 0 || this.mc.thePlayer.dimension != 2)
            {
                this.stopMusic();
            }
            
            else if (this.mc.thePlayer.dimension == 0 || this.mc.thePlayer.dimension == 2)
            {
                if (this.currentMusic != null)
                {
                    if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic))
                    {
                        this.currentMusic = null;
                        this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.random, musicType.getMinDelay(), musicType.getMaxDelay()), this.timeUntilNextMusic);
                    }
                }
                
                this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, musicType.getMaxDelay());
                
                if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0)
                {
                    this.playMusic(musicType);
                }
            }
        }
        
        if (!this.mc.getSoundHandler().isSoundPlaying(this.menuMusic))
        {
            this.menuMusic = null;
        }
    }
    
    public boolean playingMusic()
    {
        return this.currentMusic != null;
    }
    
    public boolean playingRecord()
    {
        return this.currentRecord != null;
    }
    
    public boolean playingMenuMusic()
    {
        return this.menuMusic != null;
    }
    
    public boolean playingMinecraftMusic()
    {
        return this.minecraftMusic != null;
    }
    
    public ISound getRecord()
    {
        return this.currentRecord;
    }
    
    public MusicType getRandomTrack()
    {
        int current = this.random.nextInt(7);
        
        return current == 0 ? MusicType.OTHERSIDE : current == 1 ? MusicType.FIVE : current == 2 ? MusicType.RELIC : current == 3 ? MusicType.MID_DAY : current == 4 ? MusicType.CALMOS : current == 5 ? MusicType.LAVA_GROUND : MusicType.JUNGLE_BELLE;
    }
    
    public void playMusic(MusicType musicType)
    {
        this.currentMusic = PositionedSoundRecord.func_147673_a(musicType.getMusicLocation());
        this.mc.getSoundHandler().playSound(this.currentMusic);
        this.timeUntilNextMusic = Integer.MAX_VALUE;
    }
    
    public void trackRecord(ISound iSound)
    {
        this.currentRecord = iSound;
    }
    
    public void trackMinecraftMusic(ISound iSound)
    {
        this.minecraftMusic = iSound;
    }
    
    public void playMenuMusic(MusicType musicType)
    {        
        this.menuMusic = PositionedSoundRecord.func_147673_a(musicType.getMusicLocation());
        this.mc.getSoundHandler().playSound(this.menuMusic);
    }
    
    public void stopMusic()
    {
        if (this.currentMusic != null)
        {
            this.mc.getSoundHandler().stopSound(this.currentMusic);
            this.currentMusic = null;
            this.timeUntilNextMusic = 0;
        }
    }
    
    public void stopMenuMusic()
    {
        if (this.menuMusic != null)
        {
            this.mc.getSoundHandler().stopSound(this.menuMusic);
            this.menuMusic = null;
        }
    }
    
    public void stopMinecraftMusic()
    {
        if (this.minecraftMusic != null)
        {
            this.mc.getSoundHandler().stopSound(this.minecraftMusic);
            this.minecraftMusic = null;
        }
    }
    
    public void stopAllSounds()
    {
        this.mc.getSoundHandler().stopSounds();
    }
    
    @SideOnly(Side.CLIENT)
    public static enum MusicType
    {
        CHRISTMAS(new ResourceLocation(RegistrySounds.SOUND_TYPE_CHRISTMAS), 1200, 1500),
        HALLOWEEN(new ResourceLocation(RegistrySounds.SOUND_TYPE_HALLOWEEN), 1200, 1500),
        SERVER_LOADING(new ResourceLocation(RegistrySounds.SOUND_TYPE_SERVER_LOADING), 1200, 1500),
        TITLE_THEME(new ResourceLocation(RegistrySounds.SOUND_TYPE_TITLE_THEME), 1200, 1500),
        STAFF_ROLL(new ResourceLocation(RegistrySounds.SOUND_TYPE_STAFF_ROLL), 1200, 1500),
        OTHERSIDE(new ResourceLocation(RegistrySounds.SOUND_TYPE_OTHERSIDE), 1200, 1500),
        FIVE(new ResourceLocation(RegistrySounds.SOUND_TYPE_FIVE), 1200, 1500),
        PIGSTEP(new ResourceLocation(RegistrySounds.SOUND_TYPE_PIGSTEP), 1200, 1500),
        RELIC(new ResourceLocation(RegistrySounds.SOUND_TYPE_RELIC), 1200, 1500),
        JINGLE_BELLS(new ResourceLocation(RegistrySounds.SOUND_TYPE_JINGLE_BELLS), 1200, 1500),
        MID_DAY(new ResourceLocation(RegistrySounds.SOUND_TYPE_MID_DAY), 1200, 1500),
        CALMOS(new ResourceLocation(RegistrySounds.SOUND_TYPE_CALMOS), 1200, 1500),
        LAVA_GROUND(new ResourceLocation(RegistrySounds.SOUND_TYPE_LAVA_GROUND), 1200, 1500),
        JUNGLE_BELLE(new ResourceLocation(RegistrySounds.SOUND_TYPE_JUNGLE_BELLE), 1200, 1500);
        
        private final ResourceLocation resourceLocation;
        private final int minDelay, maxDelay;
        
        private MusicType(ResourceLocation resourceLocation, int minDelay, int maxDelay)
        {
            this.resourceLocation = resourceLocation;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
        }
        
        public ResourceLocation getMusicLocation()
        {
            return this.resourceLocation;
        }
        
        public int getMinDelay()
        {
            return this.minDelay;
        }
        
        public int getMaxDelay()
        {
            return this.maxDelay;
        }
    }
}