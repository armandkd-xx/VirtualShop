package org.blockface.virtualshop.managers;

import org.bukkit.util.config.Configuration;
import org.bukkit.plugin.Plugin;

public class ConfigManager
{
    private static Configuration config;

    public static void Initialize(Plugin plugin)
    {
        config = plugin.getConfiguration();
        config.load();
        BroadcastOffers();
        UsingMySQL();
        MySQLUserName();
        MySQLHost();
        MySQLdatabase();
        MySQLport();
        MySQLPassword();
        getPort();
        config.save();
    }

	public static Boolean BroadcastOffers()
	{
		return config.getBoolean("broadcast-offers", true);
	}
        
        	public static Integer MaxItemDrops()
	{
		return config.getInt("max-item-drops", -1);
	}

    public static Integer getPort() {
        return config.getInt("MySQL.port",3306);
    }

	public static Boolean UsingMySQL()
	{
		return config.getBoolean("using-MySQL", false);
	}

	public static String MySQLUserName()
	{
		return config.getString("MySQL.username", "root");
	}

	public static String MySQLPassword()
	{
		return config.getString("MySQL.password", "password");
	}

	public static String MySQLHost()
	{
		return config.getString("MySQL.host", "localhost");
	}

	public static String MySQLdatabase()
	{
		return config.getString("MySQL.database", "minecraft");
	}

	public static Integer MySQLport()
	{
		return config.getInt("MySQL.port", 3306);
	}

}
