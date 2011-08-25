package org.blockface.virtualshop.persistance;

import com.sqlLibrary.MySQL.mysqlCore;
import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.ConfigManager;

import java.sql.ResultSet;

public class MySQL implements Database
{
    private mysqlCore mysqldb;

    public void Load() throws Exception
    {
        Chatty.LogInfo("Using MySQL.");
        mysqldb = new mysqlCore(Chatty.getLogger(), Chatty.getPrefix(), ConfigManager.MySQLHost(), ConfigManager.MySQLdatabase(), ConfigManager.MySQLUserName(), ConfigManager.MySQLPassword());
			mysqldb.initialize();
			if(mysqldb.checkConnection())
			{
				Chatty.LogInfo("Successfully connected to MySQL Database");
				CheckTables(mysqldb);
			}
			Chatty.LogInfo("Could not connect to MySQL Database. Check settings.");
    }


    private void CheckTables(mysqlCore Database) throws Exception
	{
		if(!Database.checkTable("stock"))
		{
			String query = "create table stock(`id` integer primary key auto_increment,`damage` integer,`seller` varchar(80) not null,`item` integer not null, `price` float not null,`amount` integer not null)";
			Database.createTable(query);
            Chatty.LogInfo("Created stock table.");
		}
		if(!Database.checkTable("transactions"))
		{
			String query = "create table transactions(`id` integer primary key auto_increment,`damage` integer not null, `buyer` varchar(80) not null,`seller` varchar(80) not null,`item` integer not null, `cost` float not null,`amount` integer not null)";
			mysqldb.createTable(query);
			Chatty.LogInfo("Created transaction table.");
		}
	}

    public ResultSet SelectQuery(String query)
	{
        try
        {
            return mysqldb.sqlQuery(query);
        }
        catch (Exception e)
        {
            Chatty.LogInfo("MySQL error, check connection.");
            return null;
        }
	}

    public void DeleteQuery(String query)
	{
        try
        {
				mysqldb.deleteQuery(query);
        }
        catch (Exception e)
        {
            Chatty.LogInfo("MySQL error, check connection.");
        }
	}

	public void UpdateQuery(String query)
    {
        try
        {
            mysqldb.updateQuery(query);
        }
        catch (Exception e)
        {
            Chatty.LogInfo("MySQL error, check connection.");
        }

	}

	public void InsertQuery(String query)
	{
			try
			{
				mysqldb.insertQuery(query);
			}
			catch (Exception e)
			{
				Chatty.LogInfo("MySQL error, check connection.");
			}
	}
}
