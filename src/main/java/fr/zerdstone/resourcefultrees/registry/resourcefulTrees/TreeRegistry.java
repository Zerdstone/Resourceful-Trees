package fr.zerdstone.resourcefultrees.registry.resourcefulTrees;

import fr.zerdstone.resourcefultrees.data.TreeData;

import java.util.HashMap;
import java.util.Map;

public class TreeRegistry {
	public static final TreeRegistry INSTANCE = new TreeRegistry();

	public static TreeRegistry getRegistry() {return INSTANCE;}

	Map<String, TreeData> treesData = new HashMap<>();

	public void addTreeData(String treeName, TreeData treeData) {
		this.treesData.put(treeName, treeData);
	}

	public Map<String, TreeData> getTrees() {
		return treesData;
	}
}
