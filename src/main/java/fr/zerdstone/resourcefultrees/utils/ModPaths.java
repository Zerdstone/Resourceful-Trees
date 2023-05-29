package fr.zerdstone.resourcefultrees.utils;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModPaths {
	private ModPaths() {
		throw new IllegalAccessError(ModConstants.UTILITY_CLASS);
	}

	public static final Path TREES = createCustomPath("trees");
	public static final Path RESOURCES = createCustomPath("resources");


	private static Path createCustomPath(String pathName) {
		Path customPath = Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(), ResourcefulTrees.MOD_ID, pathName);
		createDirectory(customPath, pathName);
		return customPath;
	}

	private static void createDirectory(Path path, String dirName) {
		try {
			Files.createDirectories(path);
		} catch(FileAlreadyExistsException ignored) { //ignored
		} catch(IOException e) {
			ResourcefulTrees.LOGGER.error("failed to create \"{}\" directory", dirName);
		}
	}
}
