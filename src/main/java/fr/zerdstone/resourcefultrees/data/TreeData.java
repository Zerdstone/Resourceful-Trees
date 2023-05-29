package fr.zerdstone.resourcefultrees.data;

public class TreeData {

	public worldGenerationData worldGeneration;

	public barkData bark;

	public logData log;

	public saplingData sapling;

	public String growOn;

	public leavesData leaves;

	public class worldGenerationData {
		public boolean generateInWorld;
		public String[] biomeList;
	}

	public class barkData {
		public String displayName;
		public String resultIngredient;
	}

	public class logData {
		public String displayName;
		public boolean useChiselInWorld;
		public int burnTime;
	}

	public class saplingData {
		public String displayName;
		public int burnTime;
	}

	public class leavesData {
		public String displayName;
		public boolean dropSapling;
		public boolean dropBark;
		public boolean dropStick;
	}
}
