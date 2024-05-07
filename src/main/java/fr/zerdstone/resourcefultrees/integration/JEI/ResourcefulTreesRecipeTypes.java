package fr.zerdstone.resourcefultrees.integration.JEI;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.recipe.SimpleBarkRefineryRecipe;
import mezz.jei.api.recipe.RecipeType;

public class ResourcefulTreesRecipeTypes {

	public static final RecipeType<SimpleBarkRefineryRecipe> SBR =
			RecipeType.create(ResourcefulTrees.MOD_ID, "simple_bark_refinery", SimpleBarkRefineryRecipe.class);
}
