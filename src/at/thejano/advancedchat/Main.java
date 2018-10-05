package at.thejano.advancedchat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private BoldMessageListener boldMessageListener;
    private AnnotationMessageListener annotationMessageListener;

    @Override
    public void onEnable() {
        boldMessageListener = new BoldMessageListener();
        Bukkit.getServer().getPluginManager().registerEvents(boldMessageListener, this);
        annotationMessageListener = new AnnotationMessageListener();
        Bukkit.getServer().getPluginManager().registerEvents(annotationMessageListener, this);
    }

    @Override
    public void onDisable() {

    }
}
