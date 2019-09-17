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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SafariNet {

    private ItemStack net;
    private static Map<NetType, ItemStack> emptyTypeToItem = new HashMap<>();
    private static String fullNetName = ChatColor.LIGHT_PURPLE + "Filled Safari Net";
    private Material fullNetItem = Material.GHAST_SPAWN_EGG;

    public SafariNet(ItemStack net) {
        this.net = net;
    }

    public static ItemStack getEmptyNet(NetType type) {
        return emptyTypeToItem.get(type);
    }

    public boolean isFullNet() {
        if(net.getType() == fullNetItem) {
            if(net.hasItemMeta()) {
                ItemMeta meta = net.getItemMeta();
                if (meta.hasDisplayName() && meta.getDisplayName().contains("Safari Net")) {
                    if (meta.hasLore() && meta.getLore().size() > getTypeLineIndicator()) {
                        String typeLine = meta.getLore().get(getTypeLineIndicator());
                        for(NetType netType : NetType.values()) {
                            if(typeLine.equals(netType.getNetName())) {
                                return true;
                            }
                        }
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

        ItemStack net = new ItemStack(fullNetItem);

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
        meta.setDisplayName(fullNetName);
        List<String> lore = new ArrayList<>();
        lore.add(getTypeLineIndicator(), getNetType().getNetName());
        lore.add(ChatColor.AQUA + friendlyMobName(entity.getType()));
        meta.setLore(lore);
        meta.setCustomModelData(net.getItemMeta().getCustomModelData());
        fullNet.setItemMeta(meta);
    }

    private static String friendlyMobName(EntityType type) {
        String name = type.name().toLowerCase();
        name = name.replace("_", " ");
        name = WordUtils.capitalize(name);
        return name;
    }

    public NetType getNetType() {
        if(isEmptyNet() || isFullNet()) {
            for (NetType type : NetType.values()) {
                if (net.getItemMeta().getLore().get(getTypeLineIndicator()).equals(type.getNetName())) {
                    return type;
                }
            }
        }
        return null;
    }

    public static void setEmptyTypeToItem(NetType type, ItemStack item) {
        emptyTypeToItem.put(type, item);
    }

    public static int getTypeLineIndicator() {
        return 0;
    }

    public static String getFullNetName() {
        return fullNetName;
    }
}
