package org.blockface.virtualshop.objects;

import org.blockface.virtualshop.Chatty;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Transaction
{
    public String seller;
    public String buyer;
    public ItemStack item;
    public double cost;

    public Transaction(String seller, String buyer, int id, int damage, int amount, double cost)
    {
        this.seller = seller;
        this.buyer = buyer;
        this.item = new ItemStack(id,amount,(short)damage);
        this.cost = cost;
    }

    public static List<Transaction> ListTransactions(ResultSet result)
    {
        List<Transaction> ret = new ArrayList<Transaction>();
        try {
            while(result.next())
            {
                Transaction t = new Transaction(result.getString("seller"), result.getString("buyer"),result.getInt("item"), result.getInt("damage"),result.getInt("amount"),result.getDouble("cost"));
                ret.add(t);
            }
        } catch (SQLException e) {
        }
        return ret;
    }


}
