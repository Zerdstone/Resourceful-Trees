package fr.zerdstone.resourcefultrees.utils;

import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class FileUtils {

	private static String failedToLoadSourceError(Path path) {
		return String.format("Failed to load source: %s!", path);
	}

	/**
	 * Check if file f is a json file
	 *
	 * @param f file to check
	 *
	 * @return true if json or false
	 */
	private static boolean isJson(@NotNull Path f) {
		return f.toString().endsWith(".json");
	}

	/**
	 * Loop in source path
	 *
	 * @param source  source path to loop in
	 * @param filter  filter to applied to file in source
	 * @param handler what to make with file
	 */
	private static void streamFiles(Path source, Predicate<Path> filter, Consumer<Path> handler) {
		try (Stream<Path> pathStream = Files.walk(source)) {
			pathStream.filter(filter).forEach(handler);
		}
		catch (IOException e) {
			ResourcefulTrees.LOGGER.error(failedToLoadSourceError(source), e);
		}
	}

	/**
	 * Copy default mod files
	 *
	 * @param dataPath   source path
	 * @param targetPath target path
	 * @param modRoot    path to mod resource in jar file
	 */
	public static void copyDefaultFiles(String dataPath, Path targetPath, Path modRoot) {
		if (Files.isRegularFile(modRoot)) {
			try (FileSystem fileSystem = FileSystems.newFileSystem(modRoot)) {
				streamFiles(fileSystem.getPath(dataPath), FileUtils::isJson, path -> copyFile(targetPath, path));
			}
			catch (IOException e) {
				ResourcefulTrees.LOGGER.error(failedToLoadSourceError(modRoot), e);
			}
		}
		else if (Files.isDirectory(modRoot)) {
			streamFiles(Paths.get(modRoot.toString(), dataPath), FileUtils::isJson, path1 -> copyFile(targetPath, path1));
		}
	}

	/**
	 * Copy file from source to targetPath
	 *
	 * @param targetPath target file
	 * @param source     source file
	 */
	public static void copyFile(@NotNull Path targetPath, Path source) {
		try {
			Files.copy(source, Paths.get(targetPath.toString(), source.getFileName().toString()));
		}
		catch (IOException e) {
			ResourcefulTrees.LOGGER.error("Could not copy file: {}, Target: {}", source, targetPath);
		}
	}
}
