package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FMEntity extends FMType {

	private FMEntity ancestor;

	private ClassAccessModifier accessModifier;
	
	private String tableName;
	
	private String label;
	
	private Boolean create;
	
	private Boolean update;
	
	private Boolean delete;

	private List<FMPersistentProperty> persistentProperties = new ArrayList<FMPersistentProperty>();
	private List<FMLinkedProperty> linkedProperties = new ArrayList<FMLinkedProperty>();


	private Set<FMType> importedPackages = new HashSet<FMType>();

	public FMEntity(String name, String typePackage) {
		super(name, typePackage);
	}
	
	public Set<FMType> getImportedPackages() {
		return importedPackages;
	}

	public Iterator<FMType> getImportedIterator() {
		return importedPackages.iterator();
	}

	public void addImportedPackage(FMType importedPackage) {
		importedPackages.add(importedPackage);
	}

	public int getImportedCount() {
		return importedPackages.size();
	}

	public ClassAccessModifier getAccessModifier() {
		return accessModifier;
	}

	public void setAccessModifier(ClassAccessModifier accessModifier) {
		this.accessModifier = accessModifier;
	}

	public FMEntity getAncestor() {
		return ancestor;
	}

	public void setAncestor(FMEntity ancestor) {
		this.ancestor = ancestor;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Boolean getCreate() {
		return create;
	}

	public void setCreate(Boolean create) {
		this.create = create;
	}

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public List<FMPersistentProperty> getPersistentProperties() {
		return persistentProperties;
	}

	public void setPersistentProperties(List<FMPersistentProperty> persistentProperties) {
		this.persistentProperties = persistentProperties;
	}

	public List<FMLinkedProperty> getLinkedProperties() {
		return linkedProperties;
	}

	public void setLinkedProperties(List<FMLinkedProperty> linkedProperties) {
		this.linkedProperties = linkedProperties;
	}

	public void setImportedPackages(Set<FMType> importedPackages) {
		this.importedPackages = importedPackages;
	}
	
	public void addLinkedProperties(FMLinkedProperty linkedProperty) {
		linkedProperties.add(linkedProperty);
	}
	
	public void addPersistentProperties(FMPersistentProperty persistentProperty) {
		persistentProperties.add(persistentProperty);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
