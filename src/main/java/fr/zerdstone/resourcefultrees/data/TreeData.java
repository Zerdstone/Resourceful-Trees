package fr.zerdstone.resourcefultrees.data;

public class TreeData {

	public String displayName;

	public boolean useChiselOnLog;

	public int logBurnTime;

	public int saplingBurnTime;

	public boolean leavesDropBark;

	public String growOn;

	public worldGenerationData worldGeneration;

	public resultIngredients results;

	public class worldGenerationData {
		public boolean generateInWorld;
		public String[] biomeList;
	}

	public class resultIngredients {
		public resultIngredient T0;
		public resultIngredient T1;
		public resultIngredient T2;
		public resultIngredient T3;
	}

	public class resultIngredient {
		public String item;
		public int number;
	}
}
