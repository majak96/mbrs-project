package model;

public abstract class FMNamedElement {

	private String name;

	public FMNamedElement() {
		// TODO Auto-generated constructor stub
	}

	public FMNamedElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
