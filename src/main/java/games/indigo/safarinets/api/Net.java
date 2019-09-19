package games.indigo.safarinets.api;

import games.indigo.safarinets.SafariNets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Net implements CustomRecipe {

    private NetType netType;
    private ItemStack emptyNet;
    private Recipe recipe;
    private NamespacedKey key;

    private static int typeLineIndicator = 0;
    private static String fullNetName = ChatColor.LIGHT_PURPLE + "Filled Safari Net";

    public Net(NetType netType, ItemStack emptyNet) {
        this.netType = netType;
        this.emptyNet = emptyNet;
        key = new NamespacedKey(SafariNets.getInstance(), netType.name().toLowerCase());
        SafariNet.nets.add(this);
    }

    public NetType getNetType() {
        return netType;
    }

    @Override
    public NamespacedKey getNameSpacedKey() {
        return key;
    }

    @Override
    public ItemStack getItem() {
        return emptyNet;
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    protected void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public static int getTypeLine() {
        return typeLineIndicator;
    }

    public static String getFullNetName() {
        return fullNetName;
    }

    public static ItemStack createItem(Material material, String displayName, NetType netType, int modelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        List<String> lore = new ArrayList<>();
        lore.add(getTypeLine(), netType.getNetName());
        meta.setLore(lore);
        meta.setCustomModelData(modelData);
        item.setItemMeta(meta);
        return item;
    }
}
