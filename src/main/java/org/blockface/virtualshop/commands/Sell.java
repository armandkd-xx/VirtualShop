package org.blockface.virtualshop.commands;

import com.iCo6.IO.Database;
import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.ConfigManager;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.util.InventoryManager;
import org.blockface.virtualshop.util.ItemDb;
import org.blockface.virtualshop.util.Numbers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;


public class Sell
{
    public static void Execute(CommandSender sender, String[] args)
    {
        if(!(sender instanceof Player))
        {
            Chatty.DenyConsole(sender);
            return;
        }
        if(!sender.hasPermission("virtualshop.sell"))
        {
            Chatty.NoPermissions(sender);
            return;
        }
        if(args.length < 3)
		{
			Chatty.SendError(sender, "Proper usage is /sell <amount> <item> <price>");
			return;
		}
        float price = Numbers.ParseFloat(args[2]);
		int amount = Numbers.ParseInteger(args[0]);
		if(amount < 0 || price < 0)
		{
			Chatty.NumberFormat(sender);
			return;
		}
        Player player = (Player)sender;
        ItemStack item = ItemDb.get(args[1], amount);
		if(args[1].equalsIgnoreCase("hand"))
		{
			item=new ItemStack(player.getItemInHand().getType(),amount, player.getItemInHand().getDurability());
			args[1] = ItemDb.reverseLookup(item);
		}
		if(item==null)
		{
			Chatty.WrongItem(sender, args[1]);
			return;
		}
        InventoryManager im = new InventoryManager(player);
		if(!im.contains(item,true,true))
		{
			Chatty.SendError(sender, "You do not have " + Chatty.FormatAmount(item.getAmount()) + " " + Chatty.FormatItem(args[1]));
			return;
		}
        im.remove(item, true, true);
        int a = 0;
        for(Offer o: DatabaseManager.GetSellerOffers(player.getName(),item)) {a += o.item.getAmount();}
        DatabaseManager.RemoveSellerOffers(player,item);
        item.setAmount(item.getAmount() + a);
        Offer o = new Offer(player.getName(),item,price);
		DatabaseManager.AddOffer(o);
        if(ConfigManager.BroadcastOffers())
        {
			Chatty.BroadcastOffer(o);
			return;
		}

    }
}
