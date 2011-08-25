package org.blockface.virtualshop.managers;

import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.persistance.Database;
import org.blockface.virtualshop.persistance.MySQL;
import org.blockface.virtualshop.persistance.SQLite;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DatabaseManager
{
    private static Database database;

    public static void Initialize()
    {
        if(ConfigManager.UsingMySQL()) LoadMySQL();
        else LoadSQLite();
    }

    private static void LoadSQLite() {
        database = new SQLite();
        try {
            database.Load();
        } catch (Exception e) {
            Chatty.LogInfo("Fatal error.");
        }
    }

    private static void LoadMySQL() {
        database = new MySQL();
        try {
            database.Load();
        } catch (Exception e) {
            LoadSQLite();
        }
    }

    public static void AddOffer(Offer offer)
	{
			String query = "insert into stock(seller,item,amount,price,damage) values('" +offer.getSeller() +"',"+ offer.getItem().getType().getId() + ","+offer.getItem().getAmount() +","+offer.getPrice()+"," + offer.getItem().getDurability()+")";
			database.InsertQuery(query);
	}

    public static List<Offer> GetItemOffers(ItemStack item)
	{
		String query = "select * from stock where item=" + item.getTypeId()+ " and damage=" + item.getDurability() + " order by price asc";
		return Offer.ListOffers(database.SelectQuery(query));
	}
}
