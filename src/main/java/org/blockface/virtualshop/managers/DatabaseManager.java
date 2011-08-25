package org.blockface.virtualshop.managers;

import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.objects.Transaction;
import org.blockface.virtualshop.persistance.Database;
import org.blockface.virtualshop.persistance.MySQL;
import org.blockface.virtualshop.persistance.SQLite;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
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
			String query = "insert into stock(seller,item,amount,price,damage) values('" +offer.seller +"',"+ offer.item.getType().getId() + ","+offer.item.getAmount() +","+offer.price+"," + offer.item.getDurability()+")";
			database.InsertQuery(query);
	}

    public static List<Offer> GetItemOffers(ItemStack item)
	{
		String query = "select * from stock where item=" + item.getTypeId()+ " and damage=" + item.getDurability() + " order by price asc";
		return Offer.ListOffers(database.SelectQuery(query));
	}

    public static List<Offer> GetSellerOffers(String player, ItemStack item)
	{
		String query = "select * from stock where seller = '" + player + "' and item =" + item.getTypeId() + " and damage=" + item.getDurability();
		return Offer.ListOffers(database.SelectQuery(query));
	}

    public static void RemoveSellerOffers(Player player, ItemStack item)
	{
		String query = "delete from stock where seller = '" + player.getName() + "' and item =" + item.getTypeId() + " and damage = " + item.getDurability();
		database.DeleteQuery(query);
	}

    public static void DeleteItem(int id)
	{
		String query = "delete from stock where id="+id;
		database.DeleteQuery(query);
	}

    public static void UpdateQuantity(int id, int quantity)
	{
		String query = "update stock set amount="+quantity+" where id=" + id;
		database.UpdateQuery(query);
	}

    public static void LogTransaction(Transaction transaction)
	{
		String query = "insert into transactions(seller,buyer,item,amount,cost,damage) values('" +transaction.seller +"','"+ transaction.buyer + "'," + transaction.item.getTypeId() + ","+ transaction.item.getAmount() +","+transaction.cost+","+transaction.item.getDurability()+")";
		database.InsertQuery(query);
	}
}
