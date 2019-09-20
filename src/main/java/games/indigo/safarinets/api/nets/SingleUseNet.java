package games.indigo.safarinets.api.nets;

import games.indigo.safarinets.api.Net;
import games.indigo.safarinets.api.NetType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class SingleUseNet extends Net {

    public SingleUseNet() {
        super(NetType.SINGLE_USE, Net.createItem(Material.STICK,
                ChatColor.BLUE + "Empty Safari Net",
                NetType.SINGLE_USE,
                29));
    }

    @Override
    public void createRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getNameSpacedKey(), getItem());
        recipe.shape("SBS", "BIB", "SBS");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('B', Material.IRON_BARS);
        recipe.setIngredient('I', Material.IRON_INGOT);

        Bukkit.addRecipe(recipe);
        setRecipe(recipe);

    }
}
