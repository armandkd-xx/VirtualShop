package org.blockface.virtualshop.persistance;

import com.alta189.sqlLibrary.SQLite.*;
import org.blockface.virtualshop.Chatty;

import java.sql.ResultSet;

public class SQLite implements Database
{
    private sqlCore db;

    public void Load() throws Exception
    {
        db = new sqlCore(Chatty.getLogger(), Chatty.getPrefix(), "VirtualShop", "plugins/VirtualShop/");
		db.initialize();
		if(!db.checkConnection())
		{
			Chatty.LogInfo("FlatFile creation failed!");
			throw new Exception("FlatFile creation failed.");
		}
		Chatty.LogInfo("Using flat files.");
		CheckTables(db);

    }

    private static void CheckTables(sqlCore Database)
	{
		if(!Database.checkTable("stock"))
		{
			String query = "create table stock('id' integer primary key,'damage' integer,'seller' varchar(80) not null,'item' integer not null, 'price' float not null,'amount' integer not null)";
			Database.createTable(query);
			Chatty.LogInfo("Created stock table.");
		}
		if(!Database.checkTable("transactions"))
		{
			String query = "create table transactions('id' integer primary key,'damage' integer not null,'buyer' varchar(80) not null,'seller' varchar(80) not null,'item' integer not null, 'cost' float not null,'amount' integer not null)";
			Database.createTable(query);
			Chatty.LogInfo("Created transaction table.");
		}
	}

    public ResultSet SelectQuery(String query)
	{
		return db.sqlQuery(query);
	}
	
	public void DeleteQuery(String query)
	{
		db.deleteQuery(query);
	}
	
	public void UpdateQuery(String query)
	{
		db.updateQuery(query);
	}
	
	public void InsertQuery(String query)
	{
		db.insertQuery(query);
	}

}
