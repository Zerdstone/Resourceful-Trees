package fr.zerdstone.resourcefultrees.data.dataPack;

import com.google.gson.JsonObject;
import net.minecraft.server.packs.PackType;

public class HiddenGenericMemoryPack extends GenericMemoryPack {
	protected HiddenGenericMemoryPack(PackType type, String id, JsonObject meta) {
		super(type, id, meta);
	}

	@Override
	public boolean isHidden() {
		return true;
	}
}
