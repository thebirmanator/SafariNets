package games.indigo.safarinets;

import games.indigo.safarinets.api.Net;
import games.indigo.safarinets.commands.GiveNetCmd;
import games.indigo.safarinets.listeners.CreatureSpawnListener;
import games.indigo.safarinets.listeners.PlayerInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

public class SafariNets extends JavaPlugin {

	private GiveNetCmd giveNetCmd = new GiveNetCmd();

	private static SafariNets instance;
	//TODO: recipes
	public void onEnable() {
	    instance = this;
	    saveDefaultConfig();
		loadNets();
		getCommand(giveNetCmd.givenet).setExecutor(giveNetCmd);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		getServer().getPluginManager().registerEvents(new CreatureSpawnListener(), this);
	}

	private void loadNets() {
		String[] netClasses = {"SingleUseNet", "MultiUseNet"};
		String packageName = "games.indigo.safarinets.api.nets";
		for(String netClassName : netClasses) {
			try {
				Class<?> netClass = Class.forName(packageName + "." + netClassName);
				Constructor<?> netConstructor = netClass.getConstructor();
				Net net = (Net) netConstructor.newInstance();
				net.createRecipe();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static SafariNets getInstance() {
	    return instance;
    }
}
