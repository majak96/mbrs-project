package model;

import java.util.ArrayList;
import java.util.List;

public class FMModel {

	private static FMModel instance;

	private List<FMEntity> entities = new ArrayList<FMEntity>();
	private List<FMAssociation> associations = new ArrayList<FMAssociation>();
	
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

	public List<FMAssociation> getAssociations() {
		return associations;
	}

	public void setAssociations(List<FMAssociation> associations) {
		this.associations = associations;
	}

}
