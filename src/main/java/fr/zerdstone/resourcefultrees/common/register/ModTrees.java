package fr.zerdstone.resourcefultrees.common.register;

import fr.zerdstone.resourcefultrees.datagen.TreeData;

import java.util.HashMap;
import java.util.Map;

public class ModTrees {
	public static final ModTrees INSTANCE = new ModTrees();

	public static ModTrees getRegistry() {
		return INSTANCE;
	}

	Map<String, TreeData> treesData = new HashMap<>();

	public void addTreeData(String treeName, TreeData treeData) {
		this.treesData.put(treeName, treeData);
	}

	public Map<String, TreeData> getTrees() {
		return treesData;
	}
}
