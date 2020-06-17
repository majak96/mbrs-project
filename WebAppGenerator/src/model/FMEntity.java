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
	
	private Boolean create;
	
	private Boolean update;
	
	private Boolean delete;

	private List<FMProperty> properties = new ArrayList<FMProperty>();

	private Set<FMType> importedPackages = new HashSet<FMType>();

	public FMEntity(String name, String typePackage) {
		super(name, typePackage);
	}
	
	public FMEntity(String name, String typePackage, FMEntity ancestor, ClassAccessModifier accessModifier,
			String tableName, Boolean create, Boolean update, Boolean delete, List<FMProperty> properties,
			Set<FMType> importedPackages) {
		super(name, typePackage);
		this.ancestor = ancestor;
		this.accessModifier = accessModifier;
		this.tableName = tableName;
		this.create = create;
		this.update = update;
		this.delete = delete;
		this.properties = properties;
		this.importedPackages = importedPackages;
	}

	public List<FMProperty> getProperties() {
		return properties;
	}

	public Iterator<FMProperty> getPropertyIterator() {
		return properties.iterator();
	}

	public void addProperty(FMProperty property) {
		properties.add(property);
	}

	public int getPropertyCount() {
		return properties.size();
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
		return properties.size();
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

	public void setProperties(List<FMProperty> properties) {
		this.properties = properties;
	}

	public void setImportedPackages(Set<FMType> importedPackages) {
		this.importedPackages = importedPackages;
	}
}
