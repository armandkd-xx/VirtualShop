package org.blockface.virtualshop.commands;

import org.blockface.virtualshop.Chatty;
import org.blockface.virtualshop.util.ItemDb;
import org.blockface.virtualshop.util.Numbers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static org.blockface.virtualshop.util.Numbers;

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
        if(args.length < 4)
		{
			Chatty.SendError(sender, "Proper usage is /vs sell <amount> <item> <price>");
			return;
		}
        float price = Numbers.ParseFloat(args[3]);
		int amount = Numbers.ParseInteger(args[1]);
		if(amount < 0 || price < 0)
		{
			Chatty.NumberFormat(sender);
			return;
		}
        Player player = (Player)sender;
        ItemStack item = ItemDb.get(args[2], amount);
		if(args[2].equalsIgnoreCase("hand"))
		{
			item=new ItemStack(player.getItemInHand().getTypeId());
			item.setAmount(amount);
			args[2] = ItemDb.reverseLookup(item);
		}
		if(item==null)
		{
			Chatty.WrongItem(sender, args[2]);
			return;
		}

    }
}
