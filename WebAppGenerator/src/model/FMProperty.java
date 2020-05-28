package model;

public class FMProperty extends FMNamedElement {

	private FMType type;

	private MethodPropertyAccessModifier accessModifier;

	private Integer lower;

	private Integer upper;
	
	private Boolean unique;

	public FMProperty() {
		
	}

	public FMProperty(FMType type, MethodPropertyAccessModifier accessModifier, Integer lower, Integer upper,
			Boolean unique) {
		super();
		this.type = type;
		this.accessModifier = accessModifier;
		this.lower = lower;
		this.upper = upper;
		this.unique = unique;
	}

	public FMType getType() {
		return type;
	}

	public void setType(FMType type) {
		this.type = type;
	}

	public MethodPropertyAccessModifier getAccessModifier() {
		return accessModifier;
	}

	public void setAccessModifier(MethodPropertyAccessModifier accessModifier) {
		this.accessModifier = accessModifier;
	}

	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

}
