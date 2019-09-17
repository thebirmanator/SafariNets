package games.indigo.safarinets.listeners;

import games.indigo.safarinets.SafariNets;
import games.indigo.safarinets.api.NetType;
import games.indigo.safarinets.api.SafariNet;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event) {
	    if(event.getRightClicked() instanceof LivingEntity) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            SafariNet net = new SafariNet(item);
            if (event.getHand().equals(EquipmentSlot.HAND)) {
                if (net.isEmptyNet()) {
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        player.getInventory().remove(item);
                    }
                    // epic sound design
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 2);
                    String mobSound = "entity." + event.getRightClicked().getType().name().toLowerCase() + ".hurt";
                    player.getWorld().playSound(player.getLocation(), mobSound, 1, 1.2f);

                    ItemStack fullNet = net.capture(event.getRightClicked());
                    player.getInventory().addItem(fullNet);
                    event.setCancelled(true);
                }
            }
        }
	}

	@EventHandler
    public void onInteract(PlayerInteractEvent event) {
	    Player player = event.getPlayer();
	    if(event.hasItem() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            SafariNet net = new SafariNet(item);
            if (event.getHand() == EquipmentSlot.HAND) {
                if(net.isFullNet()) {
                    NetType netType = net.getNetType();
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 2);
                    // wait a tick so that it can spawn the mob. Will crash without this!
                    Bukkit.getScheduler().scheduleSyncDelayedTask(SafariNets.getPlugin(SafariNets.class), new Runnable() {
                        @Override
                        public void run() {
                            if(player.getGameMode() == GameMode.CREATIVE) {
                                player.getInventory().removeItem(item);
                            }
                            if(netType == NetType.MULTI_USE) {
                                player.getInventory().addItem(SafariNet.getEmptyNet(netType));
                            }
                        }
                    });
                }
            }
        }
    }
}
