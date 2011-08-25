package org.blockface.virtualshop;

import org.blockface.virtualshop.events.ServerEvents;
import org.blockface.virtualshop.managers.ConfigManager;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VirtualShop extends JavaPlugin {
    public void onDisable() {
    }

    public void onEnable() {
		Chatty.Initialize(this);
        ConfigManager.Initialize(this);
        DatabaseManager.Initialize();
        RegisterEvents();
    }

    private void RegisterEvents()
    {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, new ServerEvents(), Event.Priority.Normal, this);
    }



}
