package pl.otekplay.sectors.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import pl.otekplay.sectors.managers.GeneratorManager;

public class GeneratorRecipes {
    public static void initRecipes() {
        ShapedRecipe recipeGenerator = new ShapedRecipe(GeneratorManager.getGenerator());
        recipeGenerator.shape(new String[]{"AAA", "ABA", "AAA"});
        recipeGenerator.setIngredient('A', Material.STONE);
        recipeGenerator.setIngredient('B', Material.PISTON_BASE);
        Bukkit.addRecipe(recipeGenerator);

        ShapedRecipe recipeBoyfarmer = new ShapedRecipe(GeneratorManager.getBoyfarmer());
        recipeBoyfarmer.shape(new String[]{"AAA", "ABA", "AAA"});
        recipeBoyfarmer.setIngredient('A', Material.OBSIDIAN);
        recipeBoyfarmer.setIngredient('B', Material.GOLDEN_APPLE);
        Bukkit.addRecipe(recipeBoyfarmer);

        ShapedRecipe recipeSandfarmer = new ShapedRecipe(GeneratorManager.getSandfarmer());
        recipeSandfarmer.shape(new String[]{"AAA", "ABA", "AAA"});
        recipeSandfarmer.setIngredient('A', Material.STONE);
        recipeSandfarmer.setIngredient('B', Material.DIAMOND_BLOCK);
        Bukkit.addRecipe(recipeSandfarmer);

        ShapedRecipe recipeDigger = new ShapedRecipe(GeneratorManager.getDigger());
        recipeDigger.shape(new String[]{"AAA", "ABA", "AAA"});
        recipeDigger.setIngredient('A', Material.STONE);
        recipeDigger.setIngredient('B', Material.GOLD_BLOCK);
        Bukkit.addRecipe(recipeDigger);
    }

}
