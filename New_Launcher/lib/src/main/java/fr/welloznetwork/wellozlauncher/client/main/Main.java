package fr.welloznetwork.wellozlauncher.client.main;

import javax.swing.JOptionPane;

import fr.welloznetwork.wellozlauncher.Constants;
import fr.welloznetwork.wellozlauncher.WelloZLauncher;
import javafx.application.Application;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Class.forName(Constants.JAVA_FX_APPLICATION);
            Application.launch(WelloZLauncher.class, args);
        }
        
        catch (ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Error:\n" + e.getMessage() + " not found", Constants.ERROR, JOptionPane.ERROR_MESSAGE);
            WelloZLauncher.getInstance().getLogger().printStackTrace(e);
        };
    }
}