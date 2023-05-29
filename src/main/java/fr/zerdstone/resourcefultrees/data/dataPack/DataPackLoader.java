package fr.zerdstone.resourcefultrees.data.dataPack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.zerdstone.resourcefultrees.data.DataGen;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class DataPackLoader implements RepositorySource {

	public static final DataPackLoader INSTANCE = new DataPackLoader();
	private static final String DATAPACK_NAME = "resourcefultrees:internals_data";

	@Override
	public void loadPacks(@NotNull Consumer<Pack> packList, @NotNull Pack.PackConstructor factory) {
		try(MemoryDataPack dataPack = new MemoryDataPack("SERVER")) {
			DataGen.getServerResources().forEach((location, fileContent) -> {
				JsonElement json = JsonParser.parseString(fileContent);
				dataPack.putJson(PackType.SERVER_DATA, location, json);
			});

			Pack pack = Pack.create(DATAPACK_NAME, true, () -> dataPack, factory, Pack.Position.TOP, PackSource.BUILT_IN);

			packList.accept(pack);
		}
	}

	private static class MemoryDataPack extends HiddenGenericMemoryPack {

		private static final JsonObject META_SERVER = Util.make(new JsonObject(), meta -> {
			meta.addProperty("pack_format", PackType.SERVER_DATA.getVersion(SharedConstants.getCurrentVersion()));
			meta.addProperty("description", "Data pack for resourcefultrees mods.");
		});

		protected MemoryDataPack(String type) {
			super(PackType.SERVER_DATA, DATAPACK_NAME, META_SERVER);
		}
	}

}
