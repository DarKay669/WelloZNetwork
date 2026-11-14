package fr.welloznetwork.wellozmod.mixins.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.IOUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.client.discord.IDiscordRPC;
import fr.welloznetwork.wellozmod.client.discord.RPC;
import fr.welloznetwork.wellozmod.client.ui.CustomSplashProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

@Mixin(Minecraft.class)
public class MixinMinecraft
{
    @Shadow
    private int leftClickCounter;
    
    @Shadow
    private GameSettings gameSettings;
    
    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;createDisplay()V"))
    private void setIcon(CallbackInfo callbackInfo)
    {
        InputStream inputStream = null;
        InputStream inputStream1 = null;
        
        try
        {
            inputStream = Minecraft.class.getResourceAsStream("/assets/wellozmod/icons/icon_16x16.png");
            inputStream1 = Minecraft.class.getResourceAsStream("/assets/wellozmod/icons/icon_32x32.png");
            
            if (inputStream != null && inputStream1 != null)
            {
                Display.setIcon(new ByteBuffer[] {this.readImageToBuffer(inputStream), this.readImageToBuffer(inputStream1)});
            }
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        finally
        {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(inputStream1);
        }
    }
    
    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift = Shift.AFTER))
    private void setTitle(CallbackInfo callbackInfo)
    {
        Display.setTitle("WelloZNetwork - " + Minecraft.getMinecraft().getSession().getUsername());
        
        RPC.init();
    }
    
    @Redirect(method = "startGame", at = @At(value = "INVOKE", remap = false, target = "Lcpw/mods/fml/client/SplashProgress;drawVanillaScreen()V"))
    private void drawVanillaScreen() throws LWJGLException
    {
        CustomSplashProgress.drawVanillaScreen();
    }
    
    @Redirect(method = "startGame", at = @At(value = "INVOKE", remap = false, target = "Lcpw/mods/fml/client/SplashProgress;clearVanillaResources(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/util/ResourceLocation;)V"))
    private void clearVanillaResources(TextureManager textureManager, ResourceLocation resourceLocation)
    {
        CustomSplashProgress.clearVanillaResources(textureManager, resourceLocation);
    }
    
    @Inject(method = "shutdownMinecraftApplet", at = @At("TAIL"))
    private void shutdownDiscordRPC(CallbackInfo callbackInfo)
    {
        IDiscordRPC.INSTANCE.Discord_Shutdown();
    }
    
    @Overwrite
    public int getLimitFramerate()
    {
        return !Display.isActive() ? 10 : this.gameSettings.limitFramerate;
    }
    
    @Inject(method = "func_147116_af", at = @At("HEAD"))
    private void clickMouse(final CallbackInfo callbackInfo)
    {
        this.leftClickCounter = 0;
    }
    
    private ByteBuffer readImageToBuffer(InputStream inputStream) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int[] aint = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), (int[]) null, 0, bufferedImage.getWidth());
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * aint.length);
        
        for (int i : aint)
        {
            byteBuffer.putInt(i << 8 | i >> 24 & 255);
        }
        
        byteBuffer.flip();
        return byteBuffer;
    }
}