package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMModel {

	private static FMModel instance;

	private List<FMEntity> entities = new ArrayList<FMEntity>();
	private List<FMEnumeration> enumerations = new ArrayList<>();
	private Map<String, List<String>> groups = new HashMap<>();
	
	private FMModel() {
		// TODO Auto-generated constructor stub
	}

	public static FMModel getInstance() {
		if (instance == null) {
			instance = new FMModel();
		}

		return instance;
	}

	public List<FMEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<FMEntity> entities) {
		this.entities = entities;
	}

	public Map<String, List<String>> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, List<String>> groups) {
		this.groups = groups;
	}

	public List<FMEnumeration> getEnumerations() {
		return enumerations;
	}

	public void setEnumerations(List<FMEnumeration> enumerations) {
		this.enumerations = enumerations;
	}

}
