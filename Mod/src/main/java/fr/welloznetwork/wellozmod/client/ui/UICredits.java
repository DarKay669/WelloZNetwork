package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.client.audio.CustomMusicTicker;
import fr.welloznetwork.wellozmod.client.audio.CustomMusicTicker.MusicType;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class UICredits extends GuiScreen
{
    private static final String PATH = "wellozmod:textures/gui/menu/credits/";
    private static final ResourceLocation[] BACKGROUND_IMAGES = {new ResourceLocation(PATH + "sunshine.png"), new ResourceLocation(PATH + "chicken.png"), new ResourceLocation(PATH + "high.png"), new ResourceLocation(PATH + "isle.png"), new ResourceLocation(PATH + "isle_animal.png"), new ResourceLocation(PATH + "landscape.png"), new ResourceLocation(PATH + "squid.png"), new ResourceLocation(PATH + "sugar_cane.png"), new ResourceLocation(PATH + "sheep_sheep.png"), new ResourceLocation(PATH + "snow_pig.png"), new ResourceLocation(PATH + "mountain_pumpkin.png"), new ResourceLocation(PATH + "pumpkin.png"), new ResourceLocation(PATH + "lake.png"), new ResourceLocation(PATH + "mineshaft.png"), new ResourceLocation(PATH + "mineshaft_1.png"), new ResourceLocation(PATH + "mineshaft_2.png"), new ResourceLocation(PATH + "torch.png"), new ResourceLocation(PATH + "spawner.png"), new ResourceLocation(PATH + "lava_spawner.png"), new ResourceLocation(PATH + "water_cave.png"), new ResourceLocation(PATH + "sunrise.png"), new ResourceLocation(PATH + "lava_lake.png"), new ResourceLocation(PATH + "moonlight.png"), new ResourceLocation(PATH + "moonlight_1.png"), new ResourceLocation(PATH + "stars_in_the_sky.png")};
    private static final List<String> CREDITS_TEXT = Arrays.asList("CREATED BY: DARKAY__", "EXECUTIVE PRODUCER: DARKAY__", "EXECUTIVE DIRECTOR: DARKAY__", "PRODUCER: DARKAY__", "PROGRAMMER: DARKAY__", "GRAPHIC DESIGNER: DARKAY__", "SOUND COMPOSER: DARKAY__", "SOUND EFFECTS: DARKAY__", "SCREEN TEXT WRITER: DARKAY__", "TRANSLATION: DARKAY__", "DEBUG: DARKAY__", "SPECIAL THANKS TO: FORGE MOD LOADER", "SPECIAL THANKS TO: MINECRAFTFORGE FOR FORGEGRADLE", "SPECIAL THANKS: GRADLE INC. FOR GRADLE", "SPECIAL THANKS TO: MINECRAFT FORGE FRANCE", "SPECIAL THANKS TO: @FACTABOUTMINECRAFT ON INSTAGRAM FOR TIPS DURING GAME LOADING", "SPECIAL THANKS TO: SPONGEPOWERED FOR MIXIN", "SPECIAL THANKS TO: @VATUU ON GITHUB FOR DISCORD RPC", "SOFTWARE USED: JAVA BY ORACLE", "SOFTWARE USED: GRADLE", "SOFTWARE USED: ECLIPSE IDE BY THE ECLIPSE FOUNDATION", "SOFTWARE USED: MINECRAFT NOTE BLOCK STUDIO BY DAVID ANDREI", "SOFTWARE USED: PHOTOSHOP BY ADOBE", "SOFTWARE USED: OBFUSCATOR BY @SUPERBLAUBEERE27", "SOFTWARE USED: LAUNCH4J");
    
    private long lastUpdateTime;
    private int currentIndex = 0;
    private float alpha = 0.0F, zoom = 2.0F;
    private CustomMusicTicker customMusicTicker;
    private CustomFontRenderer pixelNES;
    
    public UICredits()
    {
        this.pixelNES = CustomFontRenderer.initTTF("Pixel-NES.otf", 20);
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {        
        if (this.currentIndex <= CREDITS_TEXT.size() && System.currentTimeMillis() - this.lastUpdateTime >= 8000L)
        {
            this.currentIndex++;
            this.lastUpdateTime = System.currentTimeMillis();
            this.alpha = 0.0F;
            this.zoom = 2.0F;
        }
        
        else if (this.currentIndex == CREDITS_TEXT.size())
        {
            this.mc.displayGuiScreen(new UIThanks());
        }
        
        this.alpha = Math.min(this.alpha + 0.02F, 1.0F);
        
        drawRect(0, 0, this.width, this.height, Color.BLACK.getRGB());
        this.mc.getTextureManager().bindTexture(BACKGROUND_IMAGES[this.currentIndex % BACKGROUND_IMAGES.length]);
        
        if (this.currentIndex < CREDITS_TEXT.size())
        {
            this.zoom = Math.max(this.zoom - 0.01F, 1.0F);
            
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, this.alpha);
            
            this.mc.getTextureManager().bindTexture(BACKGROUND_IMAGES[this.currentIndex % BACKGROUND_IMAGES.length]);
            
            Tessellator tessellator = Tessellator.instance;
            
            float scale = Math.min(this.width / 960.0F, this.height / 540.0F);
            float x = (this.width - 500.0F * scale * this.zoom) / 2.0F;
            float y = (this.height - 300.0F * scale * this.zoom) / 2.0F + -80.0F * scale;
            
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, this.alpha);
            
            tessellator.addVertexWithUV(x, y + 300.0F * scale * this.zoom, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(x + 500.0F * scale * this.zoom, y + 300.0F * scale * this.zoom, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(x + 500.0F * scale * this.zoom, y, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(x, y, 0.0D, 0.0D, 0.0D);
            
            tessellator.draw();
            
            if (this.zoom <= 1.0F && this.currentIndex < CREDITS_TEXT.size())
            {
                this.pixelNES.drawCenteredString(CREDITS_TEXT.get(this.currentIndex), this.width / 2.0F, (this.height / 2.0F) + 120.0F * scale, ((int) (this.alpha * 255.0F) << 24) | Color.WHITE.getRGB());
            }
            
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
    
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.mc.theWorld == null)
        {
            if (keyCode == 1)
            {
                this.mc.displayGuiScreen(null);
                this.customMusicTicker.stopMenuMusic();
            }
        }
    }
    
    public void initGui()
    {
        if (this.lastUpdateTime == 0L)
        {
            this.lastUpdateTime = System.currentTimeMillis();
        }
        
        if (this.customMusicTicker == null)
        {
            this.customMusicTicker = new CustomMusicTicker(this.mc);
        }
        
        if (!this.customMusicTicker.playingMenuMusic())
        {
            this.customMusicTicker.playMenuMusic(MusicType.STAFF_ROLL);
        }
    }
}