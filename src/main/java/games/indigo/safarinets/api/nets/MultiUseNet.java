package games.indigo.safarinets.api.nets;

import games.indigo.safarinets.SafariNets;
import games.indigo.safarinets.api.Net;
import games.indigo.safarinets.api.NetType;
import games.indigo.safarinets.api.SafariNet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class MultiUseNet extends Net {

    public MultiUseNet() {
        super(NetType.MULTI_USE, Net.createItem(Material.STICK,
                ChatColor.AQUA + "Empty Safari Net",
                NetType.MULTI_USE,
                SafariNets.getInstance().getConfig().getInt("model-data.multi_use")));
    }

    @Override
    public void createRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getNameSpacedKey(), getItem());
        recipe.shape("DID", "ISI", "DID");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('S', new RecipeChoice.ExactChoice(SafariNet.getEmptyNet(NetType.SINGLE_USE)));

        Bukkit.addRecipe(recipe);
        setRecipe(recipe);
    }
}
