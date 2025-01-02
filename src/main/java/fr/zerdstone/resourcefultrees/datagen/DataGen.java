package fr.zerdstone.resourcefultrees.datagen;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DataGen {

	private static final Map<ResourceLocation, String> CLIENT_RESOURCES = new HashMap<>();
	private static final Map<ResourceLocation, String> SERVER_RESOURCES = new HashMap<>();

	public static Map<ResourceLocation, String> getClientResources() {
		return Collections.unmodifiableMap(CLIENT_RESOURCES);
	}

	public static Map<ResourceLocation, String> getServerResources() {
		return Collections.unmodifiableMap(SERVER_RESOURCES);
	}


	/**
	 * Generate the common data of the trees such as lang entry, block model, loot table...
	 */
	public static void generateCommonData() {
		SERVER_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "tags/blocks/resourcefultrees_logs.json"), FileGenerator.generateLogTag());

		CLIENT_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "lang/en_us.json"), FileGenerator.generateEnglishLang());

		FileGenerator.generateLogBlockModel()
					 .forEach((fileName, fileContent) -> CLIENT_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "models/block/" + fileName + ".json"), fileContent));

		FileGenerator.generateLeavesBlockModel()
					 .forEach((fileName, fileContent) -> CLIENT_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "models/block/" + fileName + ".json"), fileContent));

		FileGenerator.generateSaplingBlockModel()
					 .forEach((fileName, fileContent) -> CLIENT_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "models/block/" + fileName + ".json"), fileContent));

		FileGenerator.generateBLockItemModel()
					 .forEach((fileName, fileContent) -> CLIENT_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "models/item/" + fileName + ".json"), fileContent));

		FileGenerator.generateItemModel()
					 .forEach((fileName, fileContent) -> CLIENT_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "models/item/" + fileName + ".json"), fileContent));

		FileGenerator.generateSimpleBlockLootTable()
					 .forEach((fileName, fileContent) -> SERVER_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "loot_tables/blocks/" + fileName + ".json"),
																			  fileContent));

		FileGenerator.generateLeavesLootTable()
					 .forEach((fileName, fileContent) -> SERVER_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "loot_tables/blocks/" + fileName + ".json"),
																			  fileContent));

		FileGenerator.generateSimpleBlockStates()
					 .forEach((fileName, fileContent) -> CLIENT_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "blockstates/" + fileName + ".json"), fileContent));

		FileGenerator.generateLogBlockStates()
					 .forEach((fileName, fileContent) -> CLIENT_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "blockstates/" + fileName + ".json"), fileContent));

		FileGenerator.generateRecipe()
					 .forEach((fileName, fileContent) -> SERVER_RESOURCES.put(new ResourceLocation(ResourcefulTrees.MOD_ID, "recipes/" + fileName + ".json"), fileContent));
	}
}

