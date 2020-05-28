package model;

public class FMType extends FMNamedElement {

	private String typePackage;

	public FMType(String name, String typePackage) {
		super(name);
		this.typePackage = typePackage;
	}

	public void setTypePackage(String typePackage) {
		this.typePackage = typePackage;
	}

	public String getTypePackage() {
		return typePackage;
	}

}
