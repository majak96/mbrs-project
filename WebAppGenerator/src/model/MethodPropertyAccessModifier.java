package model;

public enum MethodPropertyAccessModifier {
	PUBLIC("public"), 
	PRIVATE("private"), 
	PROTECTED("protected"), 
	PACKAGE_PRIVATE("");

	private String name;

	private MethodPropertyAccessModifier(String s) {
		name = s;
	}

	public String toString() {
		return name;
	}
}
