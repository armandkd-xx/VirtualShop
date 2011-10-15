package org.blockface.virtualshop.commands;

import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.managers.DatabaseManager;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.util.InventoryManager;
import org.blockface.virtualshop.util.ItemDb;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.imageio.metadata.IIOMetadata;

public class Cancel {

    public static void Execute(CommandSender sender, String[] args)
    {
        if(!(sender instanceof Player))
        {
            Chatty.DenyConsole(sender);
            return;
        }
        if(!sender.hasPermission("virtualshop.cancel"))
        {
            Chatty.NoPermissions(sender);
            return;
        }
        if(args.length < 1)
        {
            Chatty.SendError(sender, "You must specify an item.");
            return;
        }
        ItemStack item = ItemDb.get(args[0], 0);
		if(item==null)
		{
			Chatty.WrongItem(sender, args[0]);
			return;
		}
        Player player = (Player)sender;
        int total = 0;
        int offer_id = -1;
        for(Offer o: DatabaseManager.GetSellerOffers(player.getName(),item))
        {
            total += o.item.getAmount();
            // There should only be one offer for a given item from a given player, right?
            offer_id = o.id;
        }
		if(total == 0)
		{
			Chatty.SendError(sender,"You do not have any " + args[0] + " for sale.");
			return;
		}
        item.setAmount(total);
        int remaining = new InventoryManager(player).addItem(item).getAmount();
        if(remaining > 0 && offer_id > -1) {
            DatabaseManager.UpdateQuantity(offer_id, remaining);
            Chatty.SendError(sender,"Unable to complete cancellation, inventory full and no more drops allowed.");
        } else {
            DatabaseManager.RemoveSellerOffers(player,item);
        }
        Chatty.SendSuccess(sender, "Removed " + Chatty.FormatAmount(total - remaining) + " " + Chatty.FormatItem(args[0]));


    }

}
