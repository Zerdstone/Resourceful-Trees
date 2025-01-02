package fr.zerdstone.resourcefultrees.utils;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModPaths {

	/**
	 * Path to trees directory in mod game config
	 */
	public static final Path TREES = createCustomPath("trees");
	/**
	 * Path to resources directory in mod game config
	 */
	public static final Path RESOURCES = createCustomPath("resources");


	/**
	 * Create directory pathName in mod game config directory
	 *
	 * @param pathName directory Name to create
	 *
	 * @return the path that is created
	 */
	private static Path createCustomPath(String pathName) {
		Path customPath = Paths.get(FMLPaths.CONFIGDIR.get().toAbsolutePath().toString(), ResourcefulTrees.MOD_ID, pathName);
		createDirectory(customPath);
		return customPath;
	}

	/**
	 * Create a directory dirName in path directory
	 *
	 * @param path directory to create
	 */
	private static void createDirectory(Path path) {
		try {
			Files.createDirectories(path);
		}
		catch (FileAlreadyExistsException ignored) { //ignored
		}
		catch (IOException e) {
			ResourcefulTrees.LOGGER.error("failed to create \"{}\" directory", path.toString());
		}
	}
}
