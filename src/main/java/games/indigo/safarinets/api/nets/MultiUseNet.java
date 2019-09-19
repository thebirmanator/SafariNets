package games.indigo.safarinets.api.nets;

import games.indigo.safarinets.api.Net;
import games.indigo.safarinets.api.NetType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class MultiUseNet extends Net {

    public MultiUseNet() {
        super(NetType.MULTI_USE, Net.createItem(Material.STICK,
                ChatColor.AQUA + "Empty Safari Net",
                NetType.MULTI_USE,
                30));
    }

    //TODO: actual recipe
    @Override
    public void createRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getNameSpacedKey(), getItem());
        recipe.shape("III");
        recipe.setIngredient('I', Material.IRON_BLOCK);

        Bukkit.addRecipe(recipe);
        setRecipe(recipe);
    }
}
