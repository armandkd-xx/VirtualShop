package org.blockface.virtualshop.objects;

import org.bukkit.inventory.ItemStack;

import javax.persistence.Id;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Offer
{
    public ItemStack item;
    public double price;
    public String seller;
    public int id;

    public Offer(String seller, int id, short damage, double price, int amount)
    {
        this.item = new ItemStack(id,amount,damage);
        this.seller = seller;
        this.price = price;
    }

    public Offer(String seller, ItemStack item, double price)
    {
        this.seller = seller;
        this.item = item;
        this.price = price;
    }

    public static List<Offer> ListOffers(ResultSet result)
    {
        List<Offer> ret = new ArrayList<Offer>();
        try {
            while(result.next())
            {
                Offer o = new Offer(result.getString("seller"), result.getInt("item"), (short)result.getInt("damage"),result.getDouble("price"),result.getInt("amount"));
                o.id = result.getInt("id");
                ret.add(o);
            }
        } catch (SQLException e) {
        }
        return ret;
    }
}
