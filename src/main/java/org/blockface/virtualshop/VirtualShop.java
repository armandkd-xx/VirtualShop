package org.blockface.virtualshop;

import org.blockface.virtualshop.managers.ConfigManager;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VirtualShop extends JavaPlugin {
    public void onDisable() {
    }

    public void onEnable() {
		Chatty.Initialize(this);
        ConfigManager.Initialize(this);
        DatabaseManager.Initialize();
    }




}
