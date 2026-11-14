package fr.welloznetwork.wellozmod.modules.hud.client.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.wellozmod.WelloZMod;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ShaderResourcePack implements IResourcePack, IResourceManagerReloadListener
{
private final Map<ResourceLocation, String> loadedData = new HashMap<>();
    
    public void onResourceManagerReload(IResourceManager iResourceManager)
    {
        this.loadedData.clear();
    }
    
    public InputStream getInputStream(ResourceLocation resourceLocation) throws IOException
    {
        if (this.isValidPath(resourceLocation))
        {
            String s = this.loadedData.computeIfAbsent(resourceLocation, loc -> {InputStream inputStream = WelloZMod.class.getResourceAsStream("/" + resourceLocation.getResourcePath());
            
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(inputStream);
            
            try
            {
                while (scanner.hasNextLine())
                {
                    stringBuilder.append(scanner.nextLine().replaceAll("@radius@", Integer.toString(12))).append("\n");
                }
            }
            
            finally
            {
                scanner.close();
            }
            
            return stringBuilder.toString();
            
            });
            
            return new ByteArrayInputStream(s.getBytes());
        }
        
        throw new FileNotFoundException(resourceLocation.toString());
    }
    
    public boolean resourceExists(ResourceLocation resourceLocation)
    {
        return (this.isValidPath(resourceLocation) && WelloZMod.class.getResource("/" + resourceLocation.getResourcePath()) != null);
    }
    
    public Set getResourceDomains()
    {
        return (Set<String>) ImmutableSet.of("minecraft");
    }
    
    public IMetadataSection getPackMetadata(IMetadataSerializer iMetadataSerializer, String sectionName) throws IOException
    {
        return (IMetadataSection) new PackMetadataSection((IChatComponent) new ChatComponentText("Blur"), 3);
    }

    public BufferedImage getPackImage() throws IOException
    {
        throw new FileNotFoundException("pack.png");
    }
    
    public String getPackName()
    {
        return "Blur";
    }
    
    private boolean isValidPath(ResourceLocation resourceLocation)
    {
        return (resourceLocation.getResourceDomain().equals("minecraft") && resourceLocation.getResourcePath().startsWith("shaders/"));
    }
}