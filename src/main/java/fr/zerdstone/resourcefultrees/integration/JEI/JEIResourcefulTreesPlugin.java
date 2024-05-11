package fr.zerdstone.resourcefultrees.integration.JEI;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.client.gui.SimpleBarkRefineryScreen;
import fr.zerdstone.resourcefultrees.common.inventory.SimpleBarkRefineryMenu;
import fr.zerdstone.resourcefultrees.common.recipe.SimpleBarkRefineryRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIResourcefulTreesPlugin implements IModPlugin {
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return new ResourceLocation(ResourcefulTrees.MOD_ID, "jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new SimpleBarkRefineryRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
		List<SimpleBarkRefineryRecipe> recipes = rm.getAllRecipesFor(SimpleBarkRefineryRecipe.Type.INSTANCE);
		registration.addRecipes(new RecipeType<>(SimpleBarkRefineryRecipeCategory.UID, SimpleBarkRefineryRecipe.class), recipes);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(SimpleBarkRefineryScreen.class, 82, 20, 44, 46, ResourcefulTreesRecipeTypes.SBR);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(SimpleBarkRefineryMenu.class, ResourcefulTreesRecipeTypes.SBR, 0, 2, 4, 36);
	}
}
