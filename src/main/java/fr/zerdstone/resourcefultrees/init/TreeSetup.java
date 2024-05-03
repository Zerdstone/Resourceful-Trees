package fr.zerdstone.resourcefultrees.init;

import com.google.gson.JsonSyntaxException;
import fr.zerdstone.resourcefultrees.config.CommonConfig;
import fr.zerdstone.resourcefultrees.data.TreeData;
import fr.zerdstone.resourcefultrees.registry.resourcefulTrees.TreeRegistry;
import fr.zerdstone.resourcefultrees.utils.FileUtils;
import fr.zerdstone.resourcefultrees.utils.ModConstants;
import fr.zerdstone.resourcefultrees.utils.ModPaths;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;

import static fr.zerdstone.resourcefultrees.ResourcefulTrees.LOGGER;

public class TreeSetup {

	private static Path treePath;
	private static Path resourcePath;

	private TreeSetup() {
		throw new IllegalStateException(ModConstants.UTILITY_CLASS);
	}

	public static void setTreePath(Path path) {
		treePath = path;
	}

	public static Path getResourcePath() {
		return resourcePath;
	}

	public static void setResourcePath(Path path) {
		resourcePath = path;
	}

	public static void setupTrees() {
		if(Boolean.TRUE.equals(CommonConfig.GENERATE_ORE_TREES.get())) {
			LOGGER.info("Copying Ore Trees...");
			FileUtils.copyDefaultFiles("/data/resourcefultrees/default_trees/ores", ModPaths.TREES, ModConstants.MOD_ROOT);
			LOGGER.info("Copying Ore Trees finished");
		}

		if(Boolean.TRUE.equals(CommonConfig.GENERATE_GLOW_TREES.get())) {
			LOGGER.info("Copying Glow Tree...");
			FileUtils.copyFile(ModPaths.TREES, Path.of(ModConstants.MOD_ROOT.toString(), "/data/resourcefultrees/default_trees/glow_tree.json"));
			LOGGER.info("Copying Glow Tree finished");
		}

		if(Boolean.TRUE.equals(CommonConfig.GENERATE_ENDER_TREES.get())) {
			LOGGER.info("Copying Ender Tree...");
			FileUtils.copyFile(ModPaths.TREES, Path.of(ModConstants.MOD_ROOT.toString(), "/data/resourcefultrees/default_trees/ender_tree.json"));
			LOGGER.info("Copying Ender Tree finished");
		}

		if(Boolean.TRUE.equals(CommonConfig.GENERATE_SLIME_TREES.get())) {
			LOGGER.info("Copying Slime Tree...");
			FileUtils.copyFile(ModPaths.TREES, Path.of(ModConstants.MOD_ROOT.toString(), "/data/resourcefultrees/default_trees/slime_tree.json"));
			LOGGER.info("Copying Slime Tree finished");
		}

		if(Boolean.TRUE.equals(CommonConfig.GENERATE_WITHER_TREES.get())) {
			LOGGER.info("Copying Wither Tree...");
			FileUtils.copyFile(ModPaths.TREES, Path.of(ModConstants.MOD_ROOT.toString(), "/data/resourcefultrees/default_trees/wither_tree.json"));
			LOGGER.info("Copying Wither Tree finished");
		}

		if(Boolean.TRUE.equals(CommonConfig.GENERATE_BLAZE_TREES.get())) {
			LOGGER.info("Copying Blaze Tree...");
			FileUtils.copyFile(ModPaths.TREES, Path.of(ModConstants.MOD_ROOT.toString(), "/data/resourcefultrees/default_trees/blaze_tree.json"));
			LOGGER.info("Copying Blaze Tree finished");
		}

		if(Boolean.TRUE.equals(CommonConfig.GENERATE_RAINBOW_TREES.get())) {
			LOGGER.info("Copying Rainbow Tree...");
			FileUtils.copyFile(ModPaths.TREES, Path.of(ModConstants.MOD_ROOT.toString(), "/data/resourcefultrees/default_trees/rainbow_tree.json"));
			LOGGER.info("Copying Rainbow Tree finished");
		}

		LOGGER.info("Registering Custom Trees...");
		try(Stream<Path> jsonStream = Files.walk(treePath)) {
			jsonStream.filter(f -> f.getFileName().toString().endsWith(".json")).forEach(TreeSetup::addTree);
		} catch(IOException e) {
			LOGGER.error("Could not stream trees!!", e);
		}
		LOGGER.info("Registering Custom Trees finished");
	}

	private static void addTree(Path file) {
		String fileNameWithExt = file.getFileName().toString();
		String fileName = fileNameWithExt.substring(0, fileNameWithExt.indexOf('.'));
		fileName = fileName.toLowerCase(Locale.ENGLISH).replace(" ", "_");

		String name;
		if(!fileName.endsWith("_tree")) {
			if(fileName.contains("trees")) {
				fileName = fileName.replace("trees", "");
			}
			if(fileName.contains("tree")) {
				fileName = fileName.replace("tree", "");
			}
			if(fileName.endsWith("_")) {
				fileName = fileName.substring(0, fileName.length() - 1);
			}
			if(fileName.startsWith("_")) {
				fileName = fileName.substring(1);
			}
			if(fileName.contains("__")) {
				fileName = fileName.replace("__", "_");
			}
			name = fileName + "_tree";
		} else {
			name = fileName;
		}

		try {
			Reader reader = Files.newBufferedReader(file.toFile().toPath());
			try {
				TreeRegistry.getRegistry().addTreeData(name, ModConstants.GSON.fromJson(reader, TreeData.class));
			} catch(JsonSyntaxException e) {
				String exception = String.format("Error was found trying to parse tree: %s. Json is invalid, validate it here : https://jsonlint.com/", name);
				throw new JsonSyntaxException(exception);
			}
		} catch(IOException e) {
			String exception = "reader exeption";
			throw new JsonSyntaxException(exception);
		}
	}
}
