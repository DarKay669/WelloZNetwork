package fr.welloznetwork.wellozmod.mixins.client.renderer.texture;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fr.welloznetwork.wellozmod.client.ui.CustomSplashProgress;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;

@Mixin(TextureUtil.class)
public class MixinTextureUtil
{
    @Shadow
    public static void deleteTexture(int textureId) {}
    
    @Shadow
    static void bindTexture(int textureId) {}
    
    @Inject(method = "allocateTextureImpl", at = @At("HEAD"), cancellable = true)
    private static void newMethod(int glTextureId, int mipmalLevels, int width, int height, float f, CallbackInfo callbackInfo)
    {
        synchronized(CustomSplashProgress.class)
        {
            deleteTexture(glTextureId);
            bindTexture(glTextureId);
        }
        
        if (OpenGlHelper.anisotropicFilteringSupported)
        {
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 34046, f);
        }
        
        if (mipmalLevels > 0)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, mipmalLevels);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 0.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, (float) mipmalLevels);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_MAX_TEXTURE_LOD_BIAS, 0.0F);
        }
        
        for (int i = 0; i <= mipmalLevels; i++)
        {
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, i, GL11.GL_RGBA, width >> i, height >> i, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (IntBuffer) null);
        }
        
        callbackInfo.cancel();
    }
}