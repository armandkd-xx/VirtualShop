package org.blockface.virtualshop.persistance;

import lib.PatPeter.SQLibrary.MySQL;
import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.ConfigManager;

import java.net.MalformedURLException;
import java.sql.ResultSet;

public class MySQLDB implements Database
{
    private MySQL db;

    public void Load() throws Exception
    {
        Chatty.LogInfo("Using MySQL.");
        db = new MySQL(Chatty.getLogger(), Chatty.getPrefix(), ConfigManager.MySQLHost(), ConfigManager.getPort().toString(), ConfigManager.MySQLdatabase(), ConfigManager.MySQLUserName(), ConfigManager.MySQLPassword());
        db.open();
        if(db.checkConnection())
        {
            Chatty.LogInfo("Successfully connected to MySQL Database");
            CheckTables();
            return;
        }
        Chatty.LogInfo("Could not connect to MySQL Database. Check settings.");
    }

    public ResultSet Query(String query) {
        try {
            return db.query(query);
        } catch (Exception e) {
            reconnect();
            return Query(query);
        }
    }


    private void reconnect() {
        try {
            db.open();
        } catch (Exception e) {
            Chatty.LogInfo("Your database has gone offline, please switch to SQLite for stability.");
        }
    }


    private void CheckTables() throws Exception
	{
		if(!db.checkTable("stock"))
		{
			String query = "create table stock(`id` integer primary key auto_increment,`damage` integer,`seller` varchar(80) not null,`item` integer not null, `price` float not null,`amount` integer not null)";
			db.createTable(query);
            Chatty.LogInfo("Created stock table.");
		}
		if(!db.checkTable("transactions"))
		{
			String query = "create table transactions(`id` integer primary key auto_increment,`damage` integer not null, `buyer` varchar(80) not null,`seller` varchar(80) not null,`item` integer not null, `cost` float not null,`amount` integer not null)";
			db.createTable(query);
			Chatty.LogInfo("Created transaction table.");
		}
	}


}
