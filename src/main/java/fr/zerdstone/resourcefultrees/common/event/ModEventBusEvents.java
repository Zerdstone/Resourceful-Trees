package fr.zerdstone.resourcefultrees.common.event;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.common.recipe.SimpleBarkRefineryRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ResourcefulTrees.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

	@SubscribeEvent
	public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
		Registry.register(Registry.RECIPE_TYPE, SimpleBarkRefineryRecipe.Type.ID,
						  SimpleBarkRefineryRecipe.Type.INSTANCE);
	}
}
