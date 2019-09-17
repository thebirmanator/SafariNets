package games.indigo.safarinets;

import games.indigo.safarinets.api.NetType;
import games.indigo.safarinets.api.SafariNet;
import games.indigo.safarinets.commands.GiveNetCmd;
import games.indigo.safarinets.listeners.CreatureSpawnListener;
import games.indigo.safarinets.listeners.PlayerInteractListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SafariNets extends JavaPlugin {

	private GiveNetCmd giveNetCmd = new GiveNetCmd();
	//TODO: recipes
	public void onEnable() {
		loadNets();
		getCommand(giveNetCmd.givenet).setExecutor(giveNetCmd);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		getServer().getPluginManager().registerEvents(new CreatureSpawnListener(), this);
	}

	private void loadNets() {
		for(NetType netType : NetType.values()) {
			ItemStack itemStack = new ItemStack(Material.STICK);
			ItemMeta meta = itemStack.getItemMeta();
			String displayName;
			int typeLine = SafariNet.getTypeLineIndicator();
			List<String> lore = new ArrayList<>();
			switch (netType) {
				case SINGLE_USE:
					displayName = ChatColor.BLUE + "Empty Safari Net";
					lore.add(typeLine, netType.getNetName());
					meta.setCustomModelData(29);
					break;
				case MULTI_USE:
					displayName = ChatColor.AQUA + "Empty Safari Net";
					lore.add(typeLine, netType.getNetName());
					meta.setCustomModelData(30);
					break;
				default:
					displayName = "";
					break;
			}
			meta.setDisplayName(displayName);
			meta.setLore(lore);
			itemStack.setItemMeta(meta);
			SafariNet.setEmptyTypeToItem(netType, itemStack);
		}
	}
}
