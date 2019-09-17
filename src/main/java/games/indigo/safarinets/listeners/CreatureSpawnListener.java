package games.indigo.safarinets.listeners;

import games.indigo.safarinets.api.SafariNet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            // reset name if it matches the spawn egg name (it likes to override)
            if(SafariNet.getFullNetName().equals(event.getEntity().getCustomName())) {
                event.getEntity().setCustomName(null);
            }
        }
    }
}
