package fr.zerdstone.resourcefultrees.common.config;

import fr.zerdstone.resourcefultrees.utils.ModConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

	public static ForgeConfigSpec.BooleanValue GENERATE_ORE_TREES;
	public static ForgeConfigSpec.BooleanValue GENERATE_GLOW_TREES;
	public static ForgeConfigSpec.BooleanValue GENERATE_RAINBOW_TREES;
	public static ForgeConfigSpec.BooleanValue GENERATE_SLIME_TREES;
	public static ForgeConfigSpec.BooleanValue GENERATE_WITHER_TREES;
	public static ForgeConfigSpec.BooleanValue GENERATE_ENDER_TREES;
	public static ForgeConfigSpec.BooleanValue GENERATE_BLAZE_TREES;

	private CommonConfig() {
		throw new IllegalStateException(ModConstants.UTILITY_CLASS);
	}

	public static final ForgeConfigSpec COMMON_CONFIG;

	static {
		ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

		commonBuilder.push("General Options");
		
		GENERATE_ORE_TREES = commonBuilder
				.comment("Define if ore tree are existing")
				.define("generateOreTree", true);
		GENERATE_GLOW_TREES = commonBuilder
				.comment("Define if glow tree are existing")
				.define("generateGlowTree", true);
		GENERATE_RAINBOW_TREES = commonBuilder
				.comment("Define if rainbow tree are existing")
				.define("generateRainbowTree", true);
		GENERATE_SLIME_TREES = commonBuilder
				.comment("Define if slime tree are existing")
				.define("generateSlimeTree", true);
		GENERATE_WITHER_TREES = commonBuilder
				.comment("Define if wither tree are existing")
				.define("generateWitherTree", true);
		GENERATE_ENDER_TREES = commonBuilder
				.comment("Define if ender tree are existing")
				.define("generateEnderTree", true);
		GENERATE_BLAZE_TREES = commonBuilder
				.comment("Define if blaze tree are existing")
				.define("generateBlazeTree", true);

		commonBuilder.pop();

		COMMON_CONFIG = commonBuilder.build();
	}
}
