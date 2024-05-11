package fr.zerdstone.resourcefultrees.common.register;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import fr.zerdstone.resourcefultrees.common.recipe.SimpleBarkRefineryRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipe {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER = DeferredRegister
			.create(ForgeRegistries.RECIPE_SERIALIZERS, ResourcefulTrees.MOD_ID);

	public static final RegistryObject<RecipeSerializer<SimpleBarkRefineryRecipe>> SIMPLE_BARK_REFINERY_SERIALIZER = SERIALIZER
			.register("simple_bark_refinery", () -> SimpleBarkRefineryRecipe.Serializer.INSTANCE);

	public static void register(IEventBus bus) {
		SERIALIZER.register(bus);
	}

}
