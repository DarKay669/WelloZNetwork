package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import fr.welloznetwork.wellozmod.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;

@Custom(FontRenderer.class)
@SideOnly(Side.CLIENT)
public class CustomFontRenderer
{
    private int[] colorCode = new int[32];
    protected float posX, posY;
    private float red, blue, green, alpha;
    private int textColor;
    private boolean boldStyle, italicStyle, underlineStyle, strikethroughStyle;
    private GlyphPage regularGlyphPage, boldGlyphPage, italicGlyphPage, boldItalicGlyphPage;
    
    public CustomFontRenderer(GlyphPage regularGlyphPage)
    {
        this.regularGlyphPage = regularGlyphPage;
        
        for (int i = 0; i < 23; i++)
        {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;
            
            if (i == 6)
            {
                k += 85;
            }
            
            if (i >= 16)
            {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }
            
            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }
    }
    
    public CustomFontRenderer(GlyphPage regularGlyphPage, GlyphPage boldGlyphPage, GlyphPage italicGlyphPage, GlyphPage boldItalicGlyphPage)
    {
        this.regularGlyphPage = regularGlyphPage;
        this.boldGlyphPage = boldGlyphPage;
        this.italicGlyphPage = italicGlyphPage;
        this.boldItalicGlyphPage = boldItalicGlyphPage;
        
        for (int i = 0; i < 23; i++)
        {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;
            
            if (i == 6)
            {
                k += 85;
            }
            
            if (i >= 16)
            {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }
            
            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }
    }
    
    public static CustomFontRenderer init(String fontName, int fontSize)
    {
        char[] chars = new char[256];
        
        for (int i = 0; i < chars.length; i++)
        {
            chars[i] = (char) i;
        }
        
        GlyphPage regularPage;
        
        regularPage = new GlyphPage(new Font(fontName, Font.PLAIN, fontSize));
        regularPage.generateGlyphPage(chars);
        regularPage.setupTexture();
        
        return new CustomFontRenderer(regularPage);
    }
    
    public static CustomFontRenderer initTTF(String fontName, int fontSize)
    {
        char[] chars = new char[256];
        
        for (int i = 0; i < chars.length; i++)
        {
            chars[i] = (char) i;
        }
        
        try (InputStream inputStream = CustomFontRenderer.class.getResourceAsStream("/assets/wellozmod/fonts/" + fontName))
        {
            if (inputStream == null)
            {
                throw new IllegalArgumentException("Unable to find font: " + fontName);
            }
            
            GlyphPage glyphPage = new GlyphPage(Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont((float) fontSize));
            
            glyphPage.generateGlyphPage(chars);
            glyphPage.setupTexture();
            
            return new CustomFontRenderer(glyphPage);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public int drawStringWithShadow(String text, float x, float y, int color)
    {
        return this.drawString(text, x, y, color, true);
    }
    
    public int drawString(String text, float x, float y, int color)
    {
        return this.drawString(text, x, y, color, false);
    }
    
    private int drawString(String text, float x, float y, int color, boolean dropShadow)
    {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        this.resetStyles();
        int i;
        
        if (dropShadow)
        {
            i = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
            i = Math.max(i, this.renderString(text, x, y, color, false));
        }
        
        else
        {
            i = this.renderString(text, x, y, color, false);
        }
        
        return i;
    }
    
    public void drawCenteredString(String text, float x, float y, int color)
    {
        if (text == null)
        {
            return;
        }
        
        this.drawString(text, x - (this.getStringWidth(text) / 2.0F), y, color);
    }
    
    private int renderString(String text, float x, float y, int color, boolean dropShadow)
    {
        if (text == null)
        {
            return 0;
        }
        
        else
        {
            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }
            
            if (dropShadow)
            {
                color = (color & 16579836) >> 2 | color & -16777216;
            }
            
            this.red = (float) (color >> 16 & 255) / 255.0F;
            this.green = (float) (color >> 8 & 255) / 255.0F;
            this.blue = (float) (color & 255) / 255.0F;
            this.alpha = (float) (color >> 24 & 255) / 255.0F;
            
            GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
            
            this.posX = x * 2.0F;
            this.posY = y * 2.0F;
            this.renderStringAtPos(text, dropShadow);
            
            return (int) (this.posX / 4.0F);
        }
    }
    
    private void renderStringAtPos(String text, boolean dropShadow)
    {
        if (text == null)
        {
            return;
        }
        
        GlyphPage glyphPage = this.getCurrentGlyphPage();
        
        glyphPage.bindTexture();
        
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        
        boolean obfuscated = false;
        
        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            
            if (c == '\u00A7' && i + 1 < text.length())
            {
                char code = text.charAt(i++);
                int colorIndex = "0123456789abcdefklmnor".indexOf(code);
                
                if (colorIndex >= 0 && colorIndex < 16)
                {
                    this.resetStyles();
                    int newColor = this.colorCode[colorIndex];
                    
                    if (dropShadow)
                    {
                        newColor = (newColor & 16579836) >> 2 | newColor & -16777216;
                    }
                    
                    this.red = (float) (newColor >> 16 & 255) / 255.0F;
                    this.green = (float) (newColor >> 8 & 255) / 255.0F;
                    this.blue = (float) (newColor & 255) / 255.0F;
                    
                    GL11.glColor4f(this.red, this.green, this.blue, this.alpha);
                }
                
                else if (colorIndex == 16) 
                {
                    obfuscated = true;
                }
                
                else if (colorIndex == 17)
                {
                    this.boldStyle = true;
                }
                
                else if (colorIndex == 18)
                {
                    this.strikethroughStyle = true;
                }
                
                else if (colorIndex == 19)
                {
                    this.underlineStyle = true;
                }
                
                else if (colorIndex == 20)
                {
                    this.italicStyle = true;
                }
                
                else if (colorIndex == 21)
                {
                    this.resetStyles();
                }
                
                continue;
            }
            
            if (obfuscated && c != ' ')
            {
                c = (char) (33 + (int) (Math.random() * (126 - 33)));
            }
            
            this.doDraw(glyphPage.drawChar(c, this.posX, this.posY), glyphPage);
        }
        
        GL11.glPopMatrix();
        
        glyphPage.unbindTexture();
    }
    
    private void doDraw(float f, GlyphPage glyphPage)
    {
        if (this.strikethroughStyle)
        {
            Tessellator tessellator = Tessellator.instance;
            
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            
            tessellator.startDrawingQuads();
            tessellator.addVertex((double) this.posX, (double) (this.posY + (float) (glyphPage.getMaxFontHeight() / 2)), 0.0D);
            tessellator.addVertex((double) (this.posX + f), (double) (this.posY + (float) (glyphPage.getMaxFontHeight() / 2)), 0.0D);
            tessellator.addVertex((double) (this.posX + f), (double) (this.posY + (float) (glyphPage.getMaxFontHeight() / 2) - 1.0F), 0.0D);
            tessellator.addVertex((double) this.posX, (double) (this.posY + (float) (glyphPage.getMaxFontHeight() / 2) - 1.0F), 0.0D);
            tessellator.draw();
            
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        
        if (this.underlineStyle)
        {
            Tessellator tessellator = Tessellator.instance;
            
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            
            tessellator.startDrawingQuads();
            
            int l = this.underlineStyle ? -1 : 0;
            
            tessellator.addVertex((double) (this.posX + (float) l), (double)(this.posY + (float) glyphPage.getMaxFontHeight()), 0.0D);
            tessellator.addVertex((double) (this.posX + f), (double) (this.posY + (float) glyphPage.getMaxFontHeight()), 0.0D);
            tessellator.addVertex((double) (this.posX + f), (double) (this.posY + (float) glyphPage.getMaxFontHeight() - 1.0F), 0.0D);
            tessellator.addVertex((double) (this.posX + (float) l), (double) (this.posY + (float) glyphPage.getMaxFontHeight() - 1.0F), 0.0D);
            tessellator.draw();
            
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        
        this.posX += f;
    }
    
    private GlyphPage getCurrentGlyphPage()
    {
        if (this.boldStyle && this.italicStyle)
        {
            return this.boldItalicGlyphPage;
        }
        
        else if (this.boldStyle)
        {
            return this.boldGlyphPage;
        }
        
        else if (this.italicStyle)
        {
            return this.italicGlyphPage;
        }
        
        else
        {
            return this.regularGlyphPage;
        }
    }
    
    private void resetStyles()
    {
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }
    
    public int getFontHeight()
    {
        return this.regularGlyphPage.getMaxFontHeight() / 2;
    }
    
    public int getStringWidth(String text)
    {
        if (text == null)
        {
            return 0;
        }
        
        int width = 0;
        
        GlyphPage currentPage;
        
        int size = text.length();
        
        boolean on = false;
        
        for (int i = 0; i < size; i++)
        {
            char character = text.charAt(i);
            
            if (character == '\u00A7')
            {
                on = true;
            }
            
            else if (on && character >= '0' && character <= 'r')
            {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                
                if (colorIndex < 16)
                {
                    this.boldStyle = false;
                    this.italicStyle = false;
                }
                
                else if (colorIndex == 17)
                {
                    this.boldStyle = true;
                }
                
                else if (colorIndex == 20)
                {
                    this.italicStyle = true;
                }
                
                else if (colorIndex == 21)
                {
                    this.boldStyle = false;
                    this.italicStyle = false;
                }
                
                i++;
                on = false;
            }
            
            else
            {
                if (on)
                {
                    i--;
                }
                
                character = text.charAt(i);
                currentPage = this.getCurrentGlyphPage();
                width += currentPage.getWidth(character) - 8;
            }
        }
        
        return width / 2;
    }
    
    public String trimStringToWidth(String text, int width)
    {
        return this.trimStringToWidth(text, width, false);
    }
    
    public String trimStringToWidth(String text, int maxWidth, boolean reverse)
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        boolean on = false;
        
        int j = reverse ? text.length() - 1 : 0;
        int k = reverse ? -1 : 1;
        int width = 0;
        
        GlyphPage currentPage;
        
        for (int i = j; i >= 0 && i < text.length() && i < maxWidth; i += k)
        {
            char character = text.charAt(i);
            
            if (character == '\u00A7')
            {
                on = true;
            }
            
            else if (on && character >= '0' && character <= 'r')
            {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                
                if (colorIndex < 16)
                {
                    this.boldStyle = false;
                    this.italicStyle = false;
                }
                
                else if (colorIndex == 17)
                {
                    this.boldStyle = true;
                }
                
                else if (colorIndex == 20)
                {
                    this.italicStyle = true;
                }
                
                else if (colorIndex == 21)
                {
                    this.boldStyle = false;
                    this.italicStyle = false;
                }
                
                i++;
                on = false;
            }
            
            else
            {
                if (on)
                {
                    i--;
                }
                
                character = text.charAt(i);
                currentPage = this.getCurrentGlyphPage();
                width += (currentPage.getWidth(character) - 8) / 2;
            }
            
            if (i > width)
            {
                break;
            }
            
            if (reverse)
            {
                stringBuilder.insert(0, character);
            }
            
            else
            {
                stringBuilder.append(character);
            }
        }
        
        return stringBuilder.toString();
    }
    
    public void drawCenteredStringWithScaledEffect(String text, float scale, int color)
    {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        
        int x = (int) (screenWidth / scale);
        int y = (int) (screenHeight / scale);
        
        this.drawString(text, x - this.getStringWidth(text) / 2, y - 10, color, false);
        
        GL11.glPopMatrix();
    }
    
    public void drawBottomCenteredString(String text, int y, int color)
    {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(screenWidth / 1.0F, screenHeight - 10, 0.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        
        this.drawString(text, - this.getStringWidth(text) / 1, y, color, false);
        
        GL11.glPopMatrix();
    }
    
    private static class GlyphPage
    {
        private int imageSize, maxFontHeight = -1;
        private Font font;
        private boolean antiAliasing, fractionalMetrics;
        private HashMap<Character, Glyph> glyphCharacterMap = new HashMap<>();
        private BufferedImage bufferedImage;
        private DynamicTexture dynamicTexture;
        
        public GlyphPage(Font font)
        {
            this.font = font;
            this.antiAliasing = true;
            this.fractionalMetrics = true;
        }
        
        public GlyphPage(Font font, boolean antiAliasing, boolean fractionalMetrics)
        {
            this.font = font;
            this.antiAliasing = antiAliasing;
            this.fractionalMetrics = fractionalMetrics;
        }
        
        public void generateGlyphPage(char[] chars)
        {
            double maxWidth = -1, maxHeight = -1;
            
            AffineTransform affineTransform = new AffineTransform();
            FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, this.antiAliasing, this.fractionalMetrics);
            
            for (char c : chars)
            {
                Rectangle2D rectangle2D = font.getStringBounds(Character.toString(c), fontRenderContext);
                
                if (maxWidth < rectangle2D.getWidth())
                {
                    maxWidth = rectangle2D.getWidth();
                }
                
                if (maxHeight < rectangle2D.getHeight())
                {
                    maxHeight = rectangle2D.getHeight();
                }
            }
            
            maxWidth += 2;
            maxHeight += 2;
            
            this.imageSize = (int) Math.ceil(Math.max(Math.ceil(Math.sqrt(maxWidth * maxWidth * chars.length) / maxWidth), Math.ceil(Math.sqrt(maxHeight * maxHeight * chars.length) / maxHeight)) * Math.max(maxWidth, maxHeight)) + 1;
            this.bufferedImage = new BufferedImage(this.imageSize, this.imageSize, BufferedImage.TYPE_INT_ARGB);
            
            Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
            
            graphics2d.setFont(this.font);
            graphics2d.setColor(new Color(255, 255, 255, 0));
            graphics2d.fillRect(0, 0, this.imageSize, this.imageSize);
            graphics2d.setColor(Color.WHITE);
            graphics2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAliasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
            graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAliasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            
            FontMetrics fontMetrics = graphics2d.getFontMetrics();
            
            int currentCharHeight = 0, posX = 0, posY = 1;
            
            for (char c : chars)
            {
                Glyph glyph = new Glyph();
                Rectangle2D rectangle2d = fontMetrics.getStringBounds(Character.toString(c), graphics2d);
                
                glyph.width = rectangle2d.getBounds().width + 8;
                glyph.height = rectangle2d.getBounds().height;
                
                if (posY + glyph.height >= this.imageSize)
                {
                    //throw new IllegalStateException("Not all characters will fit.");
                }
                
                if (posX + glyph.width >= this.imageSize)
                {
                    posX = 0;
                    posY += currentCharHeight;
                    currentCharHeight = 0;
                }
                
                glyph.x = posX;
                glyph.y = posY;
                
                if (glyph.height > this.maxFontHeight)
                {
                    this.maxFontHeight = glyph.height;
                }
                
                if (glyph.height > currentCharHeight)
                {
                    currentCharHeight = glyph.height;
                }
                
                graphics2d.drawString(Character.toString(c), posX + 2, posY + fontMetrics.getAscent());
                
                posX += glyph.width;
                
                this.glyphCharacterMap.put(c, glyph);
            }
        }
        
        public void setupTexture()
        {
            this.dynamicTexture = new DynamicTexture(this.bufferedImage);
        }
        
        public void bindTexture()
        {
            GlStateManager.bindTexture(this.dynamicTexture.getGlTextureId());
        }
        
        public void unbindTexture()
        {
            GlStateManager.bindTexture(0);
        }
        
        public float drawChar(char c, float x, float y)
        {
            Glyph glyph = this.glyphCharacterMap.get(c);
            
            if (glyph == null)
            {
                throw new IllegalArgumentException("'" + c + "' wasn't found.");
            }
            
            float pageX = glyph.x / (float) this.imageSize;
            float pageY = glyph.y / (float) this.imageSize;
            float pageWidth = glyph.width / (float) this.imageSize;
            float pageHeight = glyph.height / (float) this.imageSize;
            float width = glyph.width;
            float height = glyph.height;
            
            GL11.glBegin(GL11.GL_TRIANGLES);
            
            GL11.glTexCoord2f(pageX + pageWidth, pageY);
            GL11.glVertex2f(x + width, y);
            
            GL11.glTexCoord2f(pageX, pageY);
            GL11.glVertex2f(x, y);
            
            GL11.glTexCoord2f(pageX, pageY + pageHeight);
            GL11.glVertex2f(x, y + height);
            
            GL11.glTexCoord2f(pageX, pageY + pageHeight);
            GL11.glVertex2f(x, y + height);
            
            GL11.glTexCoord2f(pageX + pageWidth, pageY + pageHeight);
            GL11.glVertex2f(x + width, y + height);
            
            GL11.glTexCoord2f(pageX + pageWidth, pageY);
            GL11.glVertex2f(x + width, y);
            
            GL11.glEnd();
            
            return width - 8;
        }
        
        public float getWidth(char c)
        {
            return this.glyphCharacterMap.get(c).width;
        }
        
        public int getMaxFontHeight()
        {
            return this.maxFontHeight;
        }
        
        public boolean isAntiAliasing()
        {
            return this.antiAliasing;
        }
        
        public boolean isFractionalMetrics()
        {
            return this.fractionalMetrics;
        }
        
        private class Glyph
        {
            private int x, y, width, height;
            
            Glyph() {}
            
            Glyph(int x, int y, int width, int height)
            {
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
            }
            
            public int getX()
            {
                return this.x;
            }
            
            public int getY()
            {
                return this.y;
            }
            
            public int getWidth()
            {
                return this.width;
            }
            
            public int getHeight()
            {
                return this.height;
            }
        }
    }
}