package model;

public enum ClassAccessModifier {
	PUBLIC("public"), 
	PACKAGE_PRIVATE("");

	private String name;

	private ClassAccessModifier(String s) {
		name = s;
	}

	public String toString() {
		return name;
	}
}
