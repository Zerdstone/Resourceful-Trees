package fr.zerdstone.resourcefultrees.data;

import fr.zerdstone.resourcefultrees.registry.RegistryHandler;
import fr.zerdstone.resourcefultrees.registry.resourcefulTrees.TreeRegistry;

import java.util.HashMap;
import java.util.Map;

public class FileGenerator {

	public static String generateLogTag() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"replace\": false,\"values\": [");

		RegistryHandler.LOGS.forEach((name, log) -> {
			builder.append("\"" + log.getId().toString() + "\",");
		});

		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("]}");
		return builder.toString();
	}

	public static String generateEnglishLang() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		TreeRegistry.getRegistry().getTrees().forEach((name, tree) -> {
			// logs
			generateLangEntry(builder, "block.resourcefultrees.", name.replace("_tree", ""), "_log", tree.log.displayName, "Log");

			// leaves
			generateLangEntry(builder, "block.resourcefultrees.", name.replace("_tree", ""), "_leaves", tree.leaves.displayName, "Leaves");

			// saplings
			generateLangEntry(builder, "block.resourcefultrees.", name.replace("_tree", ""), "_sapling", tree.sapling.displayName, "Sapling");

			// bark
			generateLangEntry(builder, "item.resourcefultrees.", name.replace("_tree", ""), "_bark", tree.bark.displayName, "Bark");

			if(name.contains("rgb")) {
				generateLangEntry(builder, "item.resourcefultrees.", name.replace("_tree", ""), "_sap", "RGB", "Sap");
			}

		});

		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("}");

		return builder.toString();
	}

	public static Map<String, String> generateLogBlockModel() {
		Map<String, String> logsBlockModels = new HashMap<>();

		RegistryHandler.LOGS.forEach((name, log) -> {
			// Classic
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"parent\": \"minecraft:block/cube_column\",");
			builder.append("\"textures\": {");
			builder.append("\"end\": \"minecraft:block/oak_log_top\",");
			builder.append("\"side\": \"resourcefultrees:block/" + name.replace("_tree", "_log") + "\"");
			builder.append("}");
			builder.append("}");

			String logModelFile = name.replace("_tree", "_log");
			logsBlockModels.put(logModelFile, builder.toString());

			// Horizontal
			StringBuilder builderH = new StringBuilder();
			builderH.append("{");
			builderH.append("\"parent\": \"minecraft:block/cube_column_horizontal\",");
			builderH.append("\"textures\": {");
			builderH.append("\"end\": \"minecraft:block/oak_log_top\",");
			builderH.append("\"side\": \"resourcefultrees:block/" + name.replace("_tree", "_log") + "\"");
			builderH.append("}");
			builderH.append("}");

			String logHModelFile = name.replace("_tree", "_log") + "_horizontal";
			logsBlockModels.put(logHModelFile, builder.toString());
		});

		return logsBlockModels;
	}

	public static Map<String, String> generateLeavesBlockModel() {
		Map<String, String> leavesBlockModel = new HashMap<>();

		RegistryHandler.LEAVES.forEach((name, leaf) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"parent\": \"minecraft:block/leaves\",");
			builder.append("\"textures\": {");
			builder.append("\"all\": \"resourcefultrees:block/tree_leaves\"");
			builder.append("}");
			builder.append("}");

			String leafModelFile = name.replace("_tree", "_leaves");
			leavesBlockModel.put(leafModelFile, builder.toString());
		});

		return leavesBlockModel;
	}

	public static Map<String, String> generateSaplingBlockModel() {
		Map<String, String> saplingsBlockModel = new HashMap<>();

		RegistryHandler.SAPLING.forEach((name, sapling) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"parent\": \"minecraft:block/cross\",");
			builder.append("\"textures\": {");
			builder.append("\"cross\": \"resourcefultrees:block/" + name.replace("_tree", "_sapling") + "\"");
			builder.append("}");
			builder.append("}");

			String saplingModelFile = name.replace("_tree", "_sapling");
			saplingsBlockModel.put(saplingModelFile, builder.toString());
		});

		return saplingsBlockModel;
	}

	public static Map<String, String> generateBLockItemModel() {
		Map<String, String> blocksItemModel = new HashMap<>();

		RegistryHandler.LOGS.forEach((name, log) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"parent\": \"resourcefultrees:block/" + name.replace("_tree", "_log") + "\"");
			builder.append("}");

			String logModelFile = name.replace("_tree", "_log");
			blocksItemModel.put(logModelFile, builder.toString());
		});

		RegistryHandler.LEAVES.forEach((name, leaf) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"parent\": \"resourcefultrees:block/" + name.replace("_tree", "_leaves") + "\"");
			builder.append("}");

			String leafModelFile = name.replace("_tree", "_leaves");
			blocksItemModel.put(leafModelFile, builder.toString());
		});

		return blocksItemModel;
	}

	public static Map<String, String> generateItemModel() {
		Map<String, String> itemsModel = new HashMap<>();

		RegistryHandler.SAPLING.forEach((name, sapling) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"parent\": \"minecraft:item/generated\",");
			builder.append("\"textures\": {");
			builder.append("\"layer0\": \"resourcefultrees:block/" + name.replace("_tree", "_sapling") + "\"");
			builder.append("}");
			builder.append("}");

			String saplingModelFile = name.replace("_tree", "_sapling");
			itemsModel.put(saplingModelFile, builder.toString());
		});

		RegistryHandler.BARK.forEach((name, bark) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"parent\": \"minecraft:item/generated\",");
			builder.append("\"textures\": {");
			builder.append("\"layer0\": \"resourcefultrees:item/" + name.replace("_tree", "_bark") + "\"");
			builder.append("}");
			builder.append("}");

			String barkModelFile = name.replace("_tree", "_bark");
			itemsModel.put(barkModelFile, builder.toString());
		});

		return itemsModel;
	}

	public static Map<String, String> generateSimpleBlockLootTable() {
		Map<String, String> blocksLootTable = new HashMap<>();

		RegistryHandler.LOGS.forEach((name, log) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{\"type\": \"minecraft:block\",\"pools\": [");

			builder.append("{");

			builder.append("\"rolls\": 1.0,");
			builder.append("\"bonus_rolls\": 0.0,");
			builder.append("\"entries\": [");
			builder.append("{");
			builder.append("\"type\": \"minecraft:item\",");
			builder.append("\"name\": \"resourcefultrees:" + name.replace("_tree", "_log") + "\"");
			builder.append("}");
			builder.append("],");
			builder.append("\"conditions\": [{\"condition\": \"minecraft:survives_explosion\"}]");

			builder.append("}");

			builder.append("]}");

			String logLootTablesFile = name.replace("_tree", "_log");
			blocksLootTable.put(logLootTablesFile, builder.toString());
		});

		RegistryHandler.SAPLING.forEach((name, sapling) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{\"type\": \"minecraft:block\",\"pools\": [");

			builder.append("{");

			builder.append("\"rolls\": 1.0,");
			builder.append("\"bonus_rolls\": 0.0,");
			builder.append("\"entries\": [");
			builder.append("{");
			builder.append("\"type\": \"minecraft:item\",");
			builder.append("\"name\": \"resourcefultrees:" + name.replace("_tree", "_sapling") + "\"");
			builder.append("}");
			builder.append("],");
			builder.append("\"conditions\": [{\"condition\": \"minecraft:survives_explosion\"}]");

			builder.append("}");

			builder.append("]}");

			String saplingLootTablesFile = name.replace("_tree", "_sapling");
			blocksLootTable.put(saplingLootTablesFile, builder.toString());
		});

		return blocksLootTable;
	}

	public static Map<String, String> generateLeavesLootTable() {
		Map<String, String> leavesLootTable = new HashMap<>();

		RegistryHandler.LEAVES.forEach((name, leaf) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{\"type\": \"minecraft:block\",\"pools\": [");

			builder.append("{\"rolls\": 1,\"bonus_rolls\": 0,\"entries\": [{\"type\": \"minecraft:alternatives\",\"children\": [");
			// Leaves
			builder.append("{\"type\": \"minecraft:item\",\"name\": \"resourcefultrees:" +
								   name.replace("_tree", "_leaves") +
								   "\",\"conditions\": [{\"condition\": \"minecraft:alternative\",\"terms\": [{\"condition\": \"minecraft:match_tool\",\"predicate\": {\"items\": [\"minecraft:shears\"]}},{\"condition\": \"minecraft:match_tool\",\"predicate\": {\"enchantments\": [{\"enchantment\": \"minecraft:silk_touch\",\"levels\": {\"min\": 1}}]}}]}]}");

			TreeData treeData = TreeRegistry.getRegistry().getTrees().get(name);

			if(treeData.leaves.dropSapling) {
				// Sapling
				builder.append(",{\"type\": \"minecraft:item\",\"name\": \"resourcefultrees:" +
									   name.replace("_tree", "_sapling") +
									   "\",\"conditions\": [{\"condition\": \"minecraft:survives_explosion\"},{\"condition\": \"minecraft:table_bonus\",\"enchantment\": \"minecraft:fortune\",\"chances\": [0.05,0.0625,0.083333336,0.1]}]}");
			}
			builder.append("]}]}");

			if(treeData.leaves.dropStick) {
				// Stick
				builder.append(
						",{\"rolls\": 1,\"bonus_rolls\": 0,\"entries\": [{\"type\": \"minecraft:item\",\"name\": \"minecraft:stick\",\"functions\": [{\"function\": \"minecraft:set_count\",\"count\": {\"type\": \"minecraft:uniform\",\"min\": 1,\"max\": 2},\"add\": false},{\"function\": \"minecraft:explosion_decay\"}],\"conditions\": [{\"condition\": \"minecraft:table_bonus\",\"enchantment\": \"minecraft:fortune\",\"chances\": [0.02,0.022222223,0.025,0.033333335,0.1]}]}],\"conditions\": [{\"condition\": \"minecraft:inverted\",\"term\": {\"condition\": \"minecraft:alternative\",\"terms\": [{\"condition\": \"minecraft:match_tool\",\"predicate\": {\"items\": [\"minecraft:shears\"]}},{\"condition\": \"minecraft:match_tool\",\"predicate\": {\"enchantments\": [{\"enchantment\": \"minecraft:silk_touch\",\"levels\": {\"min\": 1}}]}}]}}]}");
			}

			if(treeData.leaves.dropBark) {
				// Bark
				builder.append(",{\"rolls\": 1,\"bonus_rolls\": 0,\"entries\": [{\"type\": \"minecraft:item\",\"name\": \"resourcefultrees:" +
									   name.replace("_tree", "_bark") +
									   "\",\"functions\": [{\"function\": \"minecraft:set_count\",\"count\": {\"type\": \"minecraft:uniform\",\"min\": 1,\"max\": 2},\"add\": false},{\"function\": \"minecraft:explosion_decay\"}],\"conditions\": [{\"condition\": \"minecraft:table_bonus\",\"enchantment\": \"minecraft:fortune\",\"chances\": [0.02,0.022222223,0.025,0.033333335,0.1]}]}],\"conditions\": [{\"condition\": \"minecraft:inverted\",\"term\": {\"condition\": \"minecraft:alternative\",\"terms\": [{\"condition\": \"minecraft:match_tool\",\"predicate\": {\"items\": [\"minecraft:shears\"]}},{\"condition\": \"minecraft:match_tool\",\"predicate\": {\"enchantments\": [{\"enchantment\": \"minecraft:silk_touch\",\"levels\": {\"min\": 1}}]}}]}}]}");
			}

			builder.append("]}");

			String leafLootTablesFile = name.replace("_tree", "_leaves");
			leavesLootTable.put(leafLootTablesFile, builder.toString());
		});

		return leavesLootTable;
	}

	public static Map<String, String> generateSimpleBlockStates() {
		Map<String, String> simpleBlocksState = new HashMap<>();

		RegistryHandler.SAPLING.forEach((name, sapling) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"variants\": {");
			builder.append("\"\": {");
			builder.append("\"model\": \"resourcefultrees:block/" + name.replace("_tree", "_sapling") + "\"");
			builder.append("}");
			builder.append("}");
			builder.append("}");

			String saplingBlockStateFile = name.replace("_tree", "_sapling");
			simpleBlocksState.put(saplingBlockStateFile, builder.toString());
		});

		RegistryHandler.LEAVES.forEach((name, leaf) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"variants\": {");
			builder.append("\"\": {");
			builder.append("\"model\": \"resourcefultrees:block/" + name.replace("_tree", "_leaves") + "\"");
			builder.append("}");
			builder.append("}");
			builder.append("}");

			String leafBlockStateFile = name.replace("_tree", "_leaves");
			simpleBlocksState.put(leafBlockStateFile, builder.toString());
		});

		return simpleBlocksState;
	}

	public static Map<String, String> generateLogBlockStates() {
		Map<String, String> logsBlockStates = new HashMap<>();

		RegistryHandler.LOGS.forEach((name, log) -> {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"variants\": {");
			builder.append("\"axis=x\": {");
			builder.append("\"model\": \"resourcefultrees:block/" + name.replace("_tree", "_log") + "_horizontal\",");
			builder.append("\"x\": 90,");
			builder.append("\"y\": 90");
			builder.append("},");
			builder.append("\"axis=y\": {");
			builder.append("\"model\": \"resourcefultrees:block/" + name.replace("_tree", "_log") + "\"");
			builder.append("},");
			builder.append("\"axis=z\": {");
			builder.append("\"model\": \"resourcefultrees:block/" + name.replace("_tree", "_log") + "_horizontal\",");
			builder.append("\"x\": 90");
			builder.append("}");
			builder.append("}");
			builder.append("}");

			String logBlockStateFile = name.replace("_tree", "_log");
			logsBlockStates.put(logBlockStateFile, builder.toString());
		});

		return logsBlockStates;
	}

	private static void generateLangEntry(StringBuilder builder, String prefix, String name, String suffix, String displayName, String displaySuffix) {
		builder.append("\"");
		builder.append(prefix);
		builder.append(name);
		builder.append(suffix);
		builder.append("\": \"");
		builder.append(displayName);
		builder.append(" ");
		builder.append(displaySuffix);
		builder.append("\",");
	}
}
