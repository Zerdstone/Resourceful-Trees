package fr.zerdstone.resourcefultrees.data;

import fr.zerdstone.resourcefultrees.registry.RegistryHandler;
import fr.zerdstone.resourcefultrees.registry.resourcefulTrees.TreeRegistry;
import fr.zerdstone.resourcefultrees.utils.ModConstants;

import java.util.HashMap;
import java.util.Map;

import static fr.zerdstone.resourcefultrees.ResourcefulTrees.LOGGER;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
			generateLangEntry(builder, "block.resourcefultrees.", name.replace("_tree", ""), "_log",
					tree.log.displayName, "Log");

			// leaves
			generateLangEntry(builder, "block.resourcefultrees.", name.replace("_tree", ""), "_leaves",
					tree.leaves.displayName, "Leaves");

			// saplings
			generateLangEntry(builder, "block.resourcefultrees.", name.replace("_tree", ""), "_sapling",
					tree.sapling.displayName, "Sapling");

			// bark
			generateLangEntry(builder, "item.resourcefultrees.", name.replace("_tree", ""), "_bark",
					tree.bark.displayName, "Bark");

			if (name.contains("rainbow")) {
				generateLangEntry(builder, "item.resourcefultrees.", name.replace("_tree", ""), "_sap", "Rainbow",
						"Sap");
			}

		});

		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("}");

		return builder.toString();
	}

	public static Map<String, String> generateLogBlockModel() {
		Map<String, String> logsBlockModels = new HashMap<>();

		Path classicLogFilePath = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/classicLogBlock.json");

		Path horizontalLogFilePath = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/horizontalLogBlock.json");

		RegistryHandler.LOGS.forEach((name, log) -> {
			// Classic
			String logModelFileName = name.replace("_tree", "_log");
			try {
				String classicLogBlock = Files.readString(classicLogFilePath);
				classicLogBlock = classicLogBlock.replace("LOGNAME", name.replace("_tree", "_log"));
				logsBlockModels.put(logModelFileName, classicLogBlock);
			} catch (IOException e) {
				LOGGER.error(e);
			}

			// Horizontal
			String logHModelFileName = name.replace("_tree", "_log") + "_horizontal";
			try {
				String horizontalLogBlock = Files.readString(horizontalLogFilePath);
				horizontalLogBlock = horizontalLogBlock.replace("LOGNAME", name.replace("_tree", "_log"));
				logsBlockModels.put(logHModelFileName, horizontalLogBlock);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return logsBlockModels;
	}

	public static Map<String, String> generateLeavesBlockModel() {
		Map<String, String> leavesBlockModel = new HashMap<>();

		Path leafFilePath = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/leafBlock.json");

		RegistryHandler.LEAVES.forEach((name, leaf) -> {
			String leafModelFileName = name.replace("_tree", "_leaves");
			try {
				String leafBlock = Files.readString(leafFilePath);
				leavesBlockModel.put(leafModelFileName, leafBlock);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return leavesBlockModel;
	}

	public static Map<String, String> generateSaplingBlockModel() {
		Map<String, String> saplingsBlockModel = new HashMap<>();

		Path saplingFilePath = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/saplingBlock.json");

		RegistryHandler.SAPLING.forEach((name, sapling) -> {
			String saplingModelFileName = name.replace("_tree", "_sapling");
			try {
				String saplingBlock = Files.readString(saplingFilePath);
				saplingBlock = saplingBlock.replace("SAPLINGNAME", name.replace("_tree", "_sapling"));
				saplingsBlockModel.put(saplingModelFileName, saplingBlock);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return saplingsBlockModel;
	}

	public static Map<String, String> generateBLockItemModel() {
		Map<String, String> blocksItemModel = new HashMap<>();

		Path blockItemModel = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/blockItem.json");

		RegistryHandler.LOGS.forEach((name, log) -> {
			String logItemModelFileName = name.replace("_tree", "_log");
			try {
				String logItemBlock = Files.readString(blockItemModel);
				logItemBlock = logItemBlock.replace("BLOCKNAME", name.replace("_tree", "_log"));
				blocksItemModel.put(logItemModelFileName, logItemBlock);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		RegistryHandler.LEAVES.forEach((name, leaf) -> {
			String leafItemModelFileName = name.replace("_tree", "_leaves");
			try {
				String leafItemBlock = Files.readString(blockItemModel);
				leafItemBlock = leafItemBlock.replace("BLOCKNAME", name.replace("_tree", "_leaves"));
				blocksItemModel.put(leafItemModelFileName, leafItemBlock);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return blocksItemModel;
	}

	public static Map<String, String> generateItemModel() {
		Map<String, String> itemsModel = new HashMap<>();

		Path itemModel = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/item.json");

		RegistryHandler.SAPLING.forEach((name, sapling) -> {
			String saplingItemModelFileName = name.replace("_tree", "_sapling");
			try {
				String saplingItem = Files.readString(itemModel);
				saplingItem = saplingItem.replace("item/ITEMNAME", "block/" + name.replace("_tree", "_sapling"));
				itemsModel.put(saplingItemModelFileName, saplingItem);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		RegistryHandler.BARK.forEach((name, bark) -> {
			String barkItemModelFileName = name.replace("_tree", "_bark");
			try {
				String barkItem = Files.readString(itemModel);
				barkItem = barkItem.replace("ITEMNAME", name.replace("_tree", "_bark"));
				itemsModel.put(barkItemModelFileName, barkItem);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return itemsModel;
	}

	public static Map<String, String> generateSimpleBlockLootTable() {
		Map<String, String> blocksLootTable = new HashMap<>();

		Path SimpleBlcokLootTableModel = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/SimpleBlcokLootTable.json");

		RegistryHandler.LOGS.forEach((name, log) -> {
			String logLootTablesFileName = name.replace("_tree", "_log");
			try {
				String logLootTable = Files.readString(SimpleBlcokLootTableModel);
				logLootTable = logLootTable.replace("ITEMNAME", name.replace("_tree", "_log"));
				blocksLootTable.put(logLootTablesFileName, logLootTable);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		RegistryHandler.SAPLING.forEach((name, sapling) -> {
			String saplingLootTablesFileName = name.replace("_tree", "_sapling");
			try {
				String saplingLootTable = Files.readString(SimpleBlcokLootTableModel);
				saplingLootTable = saplingLootTable.replace("ITEMNAME", name.replace("_tree", "_sapling"));
				blocksLootTable.put(saplingLootTablesFileName, saplingLootTable);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return blocksLootTable;
	}

	public static Map<String, String> generateLeavesLootTable() {
		Map<String, String> leavesLootTable = new HashMap<>();

		Path leafLootTableModel = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/leafLootTable.json");
		Path leafBarkData = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/leafBarkData.json");

		RegistryHandler.LEAVES.forEach((name, leaf) -> {
			String leafLootTablesFileName = name.replace("_tree", "_leaves");

			TreeData treeData = TreeRegistry.getRegistry().getTrees().get(name);

			try {
				String leafLootTable = Files.readString(leafLootTableModel);
				leafLootTable = leafLootTable.replace("SAPLINGNAME", name.replace("_tree", "_sapling"));
				leafLootTable = leafLootTable.replace("LEAFNAME", name.replace("_tree", "_leaves"));

				if (treeData.leaves.dropBark) {
					String barkData = Files.readString(leafBarkData);
					barkData = barkData.replace("BARKNAME", name.replace("_tree", "_bark"));
					leafLootTable = leafLootTable.replace("BARKDATA", "," + barkData);
				} else {
					leafLootTable = leafLootTable.replace("BARKDATA", "");
				}

				leavesLootTable.put(leafLootTablesFileName, leafLootTable);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return leavesLootTable;
	}

	public static Map<String, String> generateSimpleBlockStates() {
		Map<String, String> simpleBlocksState = new HashMap<>();

		Path SimpleBlcokLootTableModel = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/simpleBlockState.json");

		RegistryHandler.SAPLING.forEach((name, sapling) -> {
			String saplingBlockStateFileName = name.replace("_tree", "_sapling");

			try {
				String saplingBlockStates = Files.readString(SimpleBlcokLootTableModel);
				saplingBlockStates = saplingBlockStates.replace("BLOCKNAME", name.replace("_tree", "_sapling"));
				simpleBlocksState.put(saplingBlockStateFileName, saplingBlockStates);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		RegistryHandler.LEAVES.forEach((name, leaf) -> {
			String leafBlockStateFileName = name.replace("_tree", "_leaves");

			try {
				String leafBlockStates = Files.readString(SimpleBlcokLootTableModel);
				leafBlockStates = leafBlockStates.replace("BLOCKNAME", name.replace("_tree", "_leaves"));
				simpleBlocksState.put(leafBlockStateFileName, leafBlockStates);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return simpleBlocksState;
	}

	public static Map<String, String> generateLogBlockStates() {
		Map<String, String> logsBlockStates = new HashMap<>();

		Path logBlcokLootTableModel = Path.of(ModConstants.MOD_ROOT.toString(),
				"/data/resourcefultrees/fileModels/logBlockState.json");

		RegistryHandler.LOGS.forEach((name, log) -> {
			String logBlockStateFileName = name.replace("_tree", "_log");
			try {
				String logBlockStates = Files.readString(logBlcokLootTableModel);
				logBlockStates = logBlockStates.replace("LOGNAME", name.replace("_tree", "_log"));
				logsBlockStates.put(logBlockStateFileName, logBlockStates);
			} catch (IOException e) {
				LOGGER.error(e);
			}
		});

		return logsBlockStates;
	}

	private static void generateLangEntry(StringBuilder builder, String prefix, String name, String suffix,
			String displayName, String displaySuffix) {
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
