package org.blockface.virtualshop;

import org.blockface.virtualshop.managers.EconomyManager;
import org.blockface.virtualshop.objects.Offer;
import org.blockface.virtualshop.util.ItemDb;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Chatty
{
    private static String prefix;
    private static Plugin plugin;
	private static Logger logger;

    public static void Initialize(Plugin p)
    {
		logger = Logger.getLogger("minecraft");
        plugin = p;
        prefix = ChatColor.DARK_GREEN + "[" + plugin.getDescription().getName() + "] " + ChatColor.WHITE;
        LogInfo(plugin.getDescription().getName() + " is loading.");
    }

	public static void LogInfo(String message)
	{
		logger.info(message);
	}

    public static void SendError(CommandSender sender, String message)
    {
        sender.sendMessage(prefix + ChatColor.RED + message);
    }

    public static void SendSuccess(CommandSender sender, String message)
    {
        sender.sendMessage(prefix + ChatColor.GREEN + message);
    }

    public static Boolean SendSuccess(String sender, String message)
    {   Player player = plugin.getServer().getPlayer(sender);
		if(player == null) return false;
        SendSuccess(player,message);
		return true;
    }

    public static void SendGlobal(String message)
    {
        plugin.getServer().broadcastMessage(prefix  + message);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void WrongItem(CommandSender sender, String item)
	{

		SendError(sender, "What is " + item + "?");
	}

	public static void DenyConsole(CommandSender sender)
	{
		SendError(sender, "You must be in-game to do this.");
	}

	public static void NumberFormat(CommandSender sender)
	{
		SendError(sender, "That is not a proper number.");
	}

	public static String FormatSeller(String seller)
	{
		return ChatColor.RED + seller + ChatColor.WHITE;
	}

	public static String FormatAmount(Integer amount)
	{
		return ChatColor.GOLD + amount.toString() + ChatColor.WHITE;
	}

	public static String FormatItem(String item)
	{
		return ChatColor.BLUE + item.toLowerCase() + ChatColor.WHITE;
	}

	public static String FormatPrice(double price)
	{
		return ChatColor.YELLOW + EconomyManager.getMethod().format(price) + ChatColor.WHITE;
	}

	public static String FormatBuyer(String buyer)
	{
		return ChatColor.AQUA + buyer.toString() + ChatColor.WHITE;
	}

	public static void NoPermissions(CommandSender sender)
	{
		SendError(sender, "You do not have permission to do this");
	}

     public static void BroadcastOffer(Offer o) {
         SendGlobal(FormatSeller(o.seller) + " is selling " + FormatAmount(o.item.getAmount()) + " " + FormatItem(ItemDb.reverseLookup(o.item)) + " for " + FormatPrice(o.price) + " each.");
    }

}
