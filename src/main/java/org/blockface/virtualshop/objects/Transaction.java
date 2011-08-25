package org.blockface.virtualshop.objects;

import org.blockface.virtualshop.Chatty;
import org.bukkit.inventory.ItemStack;

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


}
