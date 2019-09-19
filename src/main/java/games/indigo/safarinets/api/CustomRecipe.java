package games.indigo.safarinets.api;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

interface CustomRecipe {

    NamespacedKey getNameSpacedKey();

    ItemStack getItem();

    Recipe getRecipe();

    void createRecipe();
}
