package org.blockface.virtualshop.objects;

import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Offer
{
    private ItemStack item;
    private double price;
    private String seller;

    public Offer(String seller, int id, short damage, double price, int amount)
    {
        this.item = new ItemStack(id,amount,damage);
        this.seller = seller;
        this.price = price;
    }

    public double getPrice() {return this.price;}

    public ItemStack getItem() {return this.item;}

    public String getSeller() {return this.seller;}

    public static List<Offer> ListOffers(ResultSet result)
    {
        List<Offer> ret = new ArrayList<Offer>();
        try {
            while(result.next())
            {
                Offer o = new Offer(result.getString("seller"), result.getInt("id"), (short)result.getInt("damage"),result.getDouble("price"),result.getInt("amount"));
                ret.add(o);
            }
        } catch (SQLException e) {
        }
        return ret;
    }
}
