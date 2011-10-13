package org.blockface.virtualshop;

import org.blockface.bukkitstats.CallHome;
import org.blockface.virtualshop.commands.*;
import org.blockface.virtualshop.events.ServerEvents;
import org.blockface.virtualshop.managers.ConfigManager;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.blockface.virtualshop.util.ItemDb;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class VirtualShop extends JavaPlugin {
    public void onDisable() {
        DatabaseManager.Close();
    }

    public void onEnable() {
		Chatty.Initialize(this);
        ConfigManager.Initialize(this);
        DatabaseManager.Initialize();
        try {
            ItemDb.load(this.getDataFolder(),"items.csv");
        } catch (IOException e) {
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        RegisterEvents();
    }

    private void RegisterEvents()
    {
        PluginManager pm = this.getServer().getPluginManager();
        //Call Home
        CallHome.load(this);

        pm.registerEvent(Event.Type.PLUGIN_ENABLE, new ServerEvents(), Event.Priority.Normal, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(label.equalsIgnoreCase("sell")) Sell.Execute(sender,args);
        if(label.equalsIgnoreCase("buy")) Buy.Execute(sender, args);
        if(label.equalsIgnoreCase("cancel")) Cancel.Execute(sender, args);
        if(label.equalsIgnoreCase("stock")) Stock.Execute(sender, args);
        if(label.equalsIgnoreCase("sales")) Sales.Execute(sender, args);
        if(label.equalsIgnoreCase("find")) Find.Execute(sender, args);
        if(label.equalsIgnoreCase("vs")) Help.Execute(sender);
        return true;
    }
}
