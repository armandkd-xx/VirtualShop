package org.blockface.virtualshop.managers;

import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Methods;
import org.blockface.virtualshop.Chatty;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

public class EconomyManager extends ServerListener
{
	private static Methods methods = new Methods();

    public static void Initialize(Plugin plugin)
    {
		if (!methods.hasMethod())
		{
			if(methods.setMethod(plugin)) Chatty.LogInfo("Using " + methods.getMethod().getName());
		}

    }

	public static Method getMethod()
	{
		return methods.getMethod();
	}
}
