package fr.welloznetwork.wellozmod.client.renderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GlStateManager
{
    private static int activeTextureUnit = 0;
    private static TextureState[] textureState = new TextureState[8];
    
    static
    {
        for (int j = 0; j < 8; j++)
        {
            textureState[j] = new TextureState();
        }
    }
    
    public static void bindTexture(int texture)
    {
        if (texture != textureState[activeTextureUnit].textureName)
        {
            textureState[activeTextureUnit].textureName = texture;
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        }
    }
    
    static class BooleanState
    {
        private final int capability;
        private boolean currentState = false;
        
        public BooleanState(int capability)
        {
            this.capability = capability;
        }
        
        public void setDisabled()
        {
            this.setState(false);
        }
        
        public void setEnabled()
        {
            this.setState(true);
        }
        
        public void setState(boolean state)
        {
            if (state != this.currentState)
            {
                this.currentState = state;
                
                if (state)
                {
                    GL11.glEnable(this.capability);
                }
                
                else
                {
                    GL11.glEnable(this.capability);
                }
            }
        }
    }
    
    static class TextureState
    {
        public BooleanState texture2DState;
        public int textureName;
        
        private TextureState()
        {
            this.texture2DState = new BooleanState(3553);
            this.textureName = 0;
        }
    }
}