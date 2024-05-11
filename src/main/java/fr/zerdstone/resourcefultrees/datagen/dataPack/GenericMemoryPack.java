package fr.zerdstone.resourcefultrees.datagen.dataPack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GenericMemoryPack implements PackResources {

	private final HashMap<ResourceLocation, Supplier<? extends InputStream>> data = new HashMap<>();

	private final JsonObject metaData;
	private final PackType allowedType;
	private final String id;

	protected GenericMemoryPack(PackType type, String id, JsonObject meta) {
		this.metaData = meta;
		this.allowedType = type;
		this.id = id;
	}

	private static ResourceLocation createPath(String namespace, String input) {
		return ResourceLocation.tryParse(namespace + ":" + input);
	}

	private boolean isTypeAllowed(PackType type) {
		return allowedType.equals(type);
	}

	public void putData(PackType type, ResourceLocation location, Supplier<? extends InputStream> supplier) {
		if (!isTypeAllowed(type)) {
			return;
		}
		data.put(location, supplier);
	}

	public void putJson(PackType type, ResourceLocation location, JsonElement json) {
		putData(type, location, () -> new ByteArrayInputStream(new Gson().toJson(json).getBytes(StandardCharsets.UTF_8)));
	}

	@Override
	public @NotNull InputStream getRootResource(@NotNull String file) throws IOException {
		if (file.contains("/") || file.contains("\\")) {
			throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
		}
		throw new FileNotFoundException(file);
	}

	@Override
	public @NotNull InputStream getResource(@NotNull PackType type, @NotNull ResourceLocation location) throws IOException {
		if (this.hasResource(type, location)) {
			return data.get(location).get();
		}
		throw new FileNotFoundException(location.toString());
	}

	@Override
	public @NotNull Collection<ResourceLocation> getResources(@NotNull PackType pType,
															  @NotNull String pNamespace,
															  @NotNull String pPath,
															  int pMaxDepth,
															  @NotNull Predicate<String> pFilter) {
		if (!isTypeAllowed(pType)) {
			return Collections.emptyList();
		}

		return data.keySet()
				   .stream()
				   .filter(location -> location.getNamespace().equals(pNamespace))
				   .filter(location -> location.getPath().startsWith(pPath))
				   .filter(location -> pFilter.test(createPath(pNamespace, location.getPath().substring(Math.max(location.getPath().lastIndexOf('/'), 0))).toString()))
				   .collect(Collectors.toList());
	}

	@Override
	public boolean hasResource(@NotNull PackType type, @NotNull ResourceLocation location) {
		return isTypeAllowed(type) && data.containsKey(location);
	}

	@Override
	public @NotNull Set<String> getNamespaces(@NotNull PackType type) {
		if (!isTypeAllowed(type)) {
			return Collections.emptySet();
		}
		return data.keySet().stream().map(ResourceLocation::getNamespace).collect(Collectors.toSet());
	}

	@Nullable
	@Override
	public <T> T getMetadataSection(@NotNull MetadataSectionSerializer<T> serializer) {
		if (!serializer.getMetadataSectionName().equals("pack")) {
			return null;
		}
		return serializer.fromJson(metaData);
	}

	@Override
	public @NotNull String getName() {
		return id;
	}

	@Override
	public void close() {
		for (Supplier<? extends InputStream> value: data.values()) {
			try {
				value.get().close();
			}
			catch (IOException e) {
				ResourcefulTrees.LOGGER.error("Failed to close input stream", e);
			}
		}
	}
}

