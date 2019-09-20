package games.indigo.safarinets.api;

import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.*;

public class SafariNet {

    private ItemStack net;

    static Set<Net> nets = new HashSet<>();

    public SafariNet(ItemStack net) {
        this.net = net;
    }

    public boolean isFullNet() {
        if(net.hasItemMeta() && net.getItemMeta() instanceof SpawnEggMeta) {
            ItemMeta meta = net.getItemMeta();
            if (meta.hasLore() && meta.getLore().size() > Net.getTypeLine()) {
                String typeLine = meta.getLore().get(Net.getTypeLine());
                for(NetType netType : NetType.values()) {
                    if(typeLine.equals(netType.getNetName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isEmptyNet() {
        for(NetType netType : NetType.values()) {
            if(net.isSimilar(getEmptyNet(netType))) {
                return true;
            }
        }
        return false;
    }

    public ItemStack capture(Entity entity) {
        net.minecraft.server.v1_14_R1.Entity nmsEntity = ((CraftEntity)entity).getHandle();
        NBTTagCompound entityTag = new NBTTagCompound();

        // Set the new entityTag to nmsEntity's entity data
        nmsEntity.c(entityTag);

        // Remove position tag from entityTag so that the mob doesn't spawn where it was picked up
        entityTag.remove("Pos");

        String spawnEggName = entity.getType().name() + "_SPAWN_EGG";
        ItemStack net = new ItemStack(Material.valueOf(spawnEggName));

        net.minecraft.server.v1_14_R1.ItemStack nmsNet = CraftItemStack.asNMSCopy(net);

        // Set the spawn egg's nbt tag to the captured mob's entity tag
        NBTTagCompound netTag = nmsNet.getTag();
        if(netTag == null) {
            netTag = new NBTTagCompound();
            netTag.set("EntityTag", entityTag);
        }
        nmsNet.setTag(netTag);

        net = CraftItemStack.asBukkitCopy(nmsNet);
        addAesthetics(net, entity);
        entity.remove();
        return net;
    }

    private void addAesthetics(ItemStack fullNet, Entity entity) {
        ItemMeta meta = fullNet.getItemMeta();
        meta.setDisplayName(Net.getFullNetName());
        List<String> lore = new ArrayList<>();
        lore.add(Net.getTypeLine(), getNetType().getNetName());
        lore.add(ChatColor.AQUA + friendlyMobName(entity.getType()));
        meta.setLore(lore);
        meta.setCustomModelData(net.getItemMeta().getCustomModelData());
        fullNet.setItemMeta(meta);
    }

    private String friendlyMobName(EntityType type) {
        String name = type.name().toLowerCase();
        name = name.replace("_", " ");
        name = WordUtils.capitalize(name);
        return name;
    }

    public NetType getNetType() {
        if(isEmptyNet() || isFullNet()) {
            for (NetType type : NetType.values()) {
                if (net.getItemMeta().getLore().get(Net.getTypeLine()).equals(type.getNetName())) {
                    return type;
                }
            }
        }
        return null;
    }

    public static ItemStack getEmptyNet(NetType type) {
        for(Net net : nets) {
            if(net.getNetType().equals(type)) {
                return net.getItem();
            }
        }
        return null;
    }
}
