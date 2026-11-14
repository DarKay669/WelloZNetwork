package fr.welloznetwork.wellozmod.mixins.client.renderer.entity;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import fr.welloznetwork.wellozmod.util.ModUtil.RandomXoshiro256StarStar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;

@Mixin(RenderItem.class)
public class MixinRenderItem
{
    private Random random = new RandomXoshiro256StarStar();
    
    @Shadow
    public float zLevel;
    
    @Overwrite
    private void renderGlint(int unused, int posX, int posY, int width, int height)
    {
        final float timeUVSpeed = 0.00390625F;
        final Tessellator tessellator = Tessellator.instance;
        final long time = Minecraft.getSystemTime();
        
        float layerUVNoise = 4.0F;
        
        OpenGlHelper.glBlendFunc(772, 1, 0, 0);
        OpenGlHelper.glBlendFunc(772, 1, 0, 1);
        
        final int timeUVDenominator = 3000;
        final float timeUVNoise = (float) (time % (long) timeUVDenominator) / (float) timeUVDenominator * 256.0F;
        
        tessellator.startDrawingQuads();
        
        tessellator.addVertexWithUV(posX, (posY + height), this.zLevel, ((timeUVNoise + (float) height * layerUVNoise) * timeUVSpeed), ((float) height * timeUVSpeed));
        tessellator.addVertexWithUV((posX + width), (posY + height), this.zLevel, ((timeUVNoise + (float) width + (float) height * layerUVNoise) * timeUVSpeed), ((float) height * timeUVSpeed));
        tessellator.addVertexWithUV((posX + width), posY, this.zLevel, ((timeUVNoise + (float) width) * timeUVSpeed), 0.0D);
        tessellator.addVertexWithUV(posX, posY, this.zLevel, (timeUVNoise * timeUVSpeed), 0.0D);
        
        tessellator.draw();
        
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);;
    }
}