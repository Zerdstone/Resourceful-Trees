package fr.zerdstone.resourcefultrees.utils;

import com.google.gson.Gson;
import fr.zerdstone.resourcefultrees.ResourcefulTrees;
import net.minecraftforge.fml.ModList;

import java.nio.file.Path;

public class ModConstants {
	public static final Gson GSON = new Gson();
	public static final Path MOD_ROOT = ModList.get().getModFileById(ResourcefulTrees.MOD_ID).getFile().getFilePath();
}
