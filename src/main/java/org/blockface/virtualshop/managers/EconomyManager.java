package org.blockface.virtualshop.managers;

import com.LRFLEW.register.payment.Method;
import com.LRFLEW.register.payment.Methods;
import org.blockface.virtualshop.Chatty;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

public class EconomyManager extends ServerListener
{
	private static Methods methods = new Methods();

    public static void Initialize(Plugin plugin)
    {
		if (!methods.hasMethod())
		{
			if(methods.setMethod(plugin.getServer().getPluginManager())) Chatty.LogInfo("Using " + methods.getMethod().getName());
		}

    }

	public static Method getMethod()
	{
		return methods.getMethod();
	}
}
