package fr.welloznetwork.wellozlauncher.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.welloznetwork.wellozlauncher.Constants;

public class LauncherUtil
{
    public static boolean isRegisteredAccount(String username)
    {
        try
        {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(Constants.MOJANG_API + username).openConnection();
            
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            
            if (httpURLConnection.getResponseCode() != 200)
            {
                return false;
            }
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            
            bufferedReader.close();
            
            JsonObject jsonObject = JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();
            
            return jsonObject.has("id") && !jsonObject.get("id").getAsString().isEmpty();
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    private static DistributionOS distributionOS;
    
    public static DistributionOS getOperatingSystemType()
    {
        if (distributionOS == null)
        {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            
            if (OS.contains("mac") || OS.contains("darwin"))
            {
                distributionOS = DistributionOS.MACOS;
            }
            
            else if (OS.contains("win"))
            {
                distributionOS = DistributionOS.WINDOWS;
            }
            
            else if (OS.contains("nux"))
            {
                distributionOS = DistributionOS.LINUX;
            }
            
            else
            {
                distributionOS = DistributionOS.WINDOWS;
            }
        }
        
        return distributionOS;
    }
    
    public static enum Status
    {
        READ("Lecture du fichier JSON"),
        DL_LIBS("Téléchargement : mc-libraries"),
        DL_ASSETS("Téléchargement : mc-assets"),
        EXTRACT_NATIVES("Téléchargement : mc-natives"),
        FORGE("Téléchargement : Forge"),
        MODS("Téléchargement : Mods"),
        EXTERNAL_FILES("Téléchargement : Fichiers complémentaires"),
        POST_EXECUTIONS("Post-Initialization"),
        INTEGRATION("Mod-Initialization"),
        END("Lancement");
        
        private String status;
        
        Status(String status)
        {
            this.status = status;
        }
        
        public String getStatus()
        {
            return this.status;
        }
    }
    
    public enum DistributionOS
    {
        WINDOWS, LINUX, MACOS;
    }
}