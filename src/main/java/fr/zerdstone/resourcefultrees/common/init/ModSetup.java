package fr.zerdstone.resourcefultrees.common.init;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static fr.zerdstone.resourcefultrees.ResourcefulTrees.LOGGER;

public class ModSetup {

	private static Path resourcePath;

	private ModSetup() {
		throw new IllegalStateException("Utility Class");
	}

	public static void initialize() {
		setupPaths();
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ModSetup::loadResources);
	}

	private static void setupPaths() {
		LOGGER.info("Setting up config paths...");
		Path configPath = FMLPaths.CONFIGDIR.get();
		Path treesPath = Paths.get(configPath.toAbsolutePath().toString(), ResourcefulTrees.MOD_ID, "trees");
		Path resourcesPath = Paths.get(configPath.toAbsolutePath().toString(), ResourcefulTrees.MOD_ID, "resources");
		resourcePath = resourcesPath;
		TreeSetup.setTreePath(treesPath);
		TreeSetup.setResourcePath(resourcesPath);

		try {
			Files.createDirectories(treesPath);
		}
		catch (FileAlreadyExistsException ignored) { //ignored
		}
		catch (IOException e) {
			LOGGER.error("failed to create \"trees\" directory");
		}

		try {
			Files.createDirectory(resourcesPath);
		}
		catch (FileAlreadyExistsException ignored) { //ignored
		}
		catch (IOException e) {
			LOGGER.error("Failed to create \"resources\" directory");
		}

		try (FileWriter file = new FileWriter(Paths.get(resourcesPath.toAbsolutePath().toString(), "pack.mcmeta").toFile())) {
			String mcMetaContent = "{\"pack\":{\"pack_format\":8,\"description\":\"\"}}";
			file.write(mcMetaContent);
		}
		catch (FileAlreadyExistsException ignored) { //ignored
		}
		catch (IOException e) {
			LOGGER.error("Failed to create pack.mcmeta file for resource loading");
		}

		try {
			Files.createDirectory(Paths.get(resourcesPath.toAbsolutePath().toString(), "/assets"));
			Files.createDirectory(Paths.get(resourcesPath.toAbsolutePath().toString(), "/assets/resourcefultrees"));
			Files.createDirectory(Paths.get(resourcesPath.toAbsolutePath().toString(), "/assets/resourcefultrees/textures"));
			Files.createDirectory(Paths.get(resourcesPath.toAbsolutePath().toString(), "/assets/resourcefultrees/textures/block"));
			Files.createDirectory(Paths.get(resourcesPath.toAbsolutePath().toString(), "/assets/resourcefultrees/textures/item"));
		}
		catch (FileAlreadyExistsException ignored) { //ignored
		}
		catch (IOException e) {
			LOGGER.error("Failed to create \"assets\" structure directory's");
		}
	}

	public static void loadResources() {
		Minecraft.getInstance().getResourcePackRepository().addPackFinder((consumer, factory) -> {
			final Pack packInfo = Pack.create(ResourcefulTrees.MOD_ID, true, () -> new FolderPackResources(resourcePath.toFile()) {
				@Override
				public boolean isHidden() {
					return true;
				}
			}, factory, Pack.Position.TOP, PackSource.BUILT_IN);
			if (packInfo == null) {
				LOGGER.error("Failed to load resource pack, some things may not work.");
				return;
			}
			consumer.accept(packInfo);
		});

	}
}
