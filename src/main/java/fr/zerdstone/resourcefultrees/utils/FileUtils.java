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

	private static boolean isJson(@NotNull Path f) {
		return f.toString().endsWith(".json");
	}

	private static void streamFiles(Path source, Predicate<Path> filter, Consumer<Path> handler) {
		try(Stream<Path> pathStream = Files.walk(source)) {
			pathStream.filter(filter).forEach(handler);
		} catch(IOException e) {
			ResourcefulTrees.LOGGER.error(failedToLoadSourceError(source), e);
		}
	}

	public static void copyDefaultFiles(String dataPath, Path targetPath, Path modRoot) {
		if(Files.isRegularFile(modRoot)) {
			try(FileSystem fileSystem = FileSystems.newFileSystem(modRoot)) {
				streamFiles(fileSystem.getPath(dataPath), FileUtils::isJson, path -> copyFile(targetPath, path));
			} catch(IOException e) {
				ResourcefulTrees.LOGGER.error(failedToLoadSourceError(modRoot), e);
			}
		} else if(Files.isDirectory(modRoot)) {
			streamFiles(Paths.get(modRoot.toString(), dataPath), FileUtils::isJson, path1 -> copyFile(targetPath, path1));
		}
	}

	public static void copyFile(@NotNull Path targetPath, Path source) {
		try {
			Files.copy(source, Paths.get(targetPath.toString(), source.getFileName().toString()));
		} catch(IOException e) {
			ResourcefulTrees.LOGGER.error("Could not copy file: {}, Target: {}", source, targetPath);
		}
	}
}
