package fr.zerdstone.resourcefultrees.datagen.dataPack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.zerdstone.resourcefultrees.datagen.DataGen;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class AssetsPackLoader implements RepositorySource {

	public static final AssetsPackLoader INSTANCE = new AssetsPackLoader();
	private static final String DATAPACK_NAME = "resourcefultrees:internals_assets";

	@Override
	public void loadPacks(@NotNull Consumer<Pack> packList, @NotNull Pack.PackConstructor factory) {
		try (MemoryDataPack dataPack = new MemoryDataPack()) {
			DataGen.getClientResources().forEach((location, fileContent) -> {
				JsonElement json = JsonParser.parseString(fileContent);
				dataPack.putJson(PackType.CLIENT_RESOURCES, location, json);
			});

			Pack pack = Pack.create(DATAPACK_NAME, true, () -> dataPack, factory, Pack.Position.TOP, PackSource.BUILT_IN);

			packList.accept(pack);
		}
	}

	private static class MemoryDataPack extends HiddenGenericMemoryPack {

		private static final JsonObject META_CLIENT = Util.make(new JsonObject(), meta -> {
			meta.addProperty("pack_format", PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion()));
			meta.addProperty("description", "Resource pack for resourcefultrees mods.");
		});

		protected MemoryDataPack() {
			super(PackType.CLIENT_RESOURCES, DATAPACK_NAME, META_CLIENT);
		}
	}
}
