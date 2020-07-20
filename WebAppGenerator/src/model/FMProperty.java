package model;

public class FMProperty extends FMNamedElement {

	private FMType type;

	private MethodPropertyAccessModifier accessModifier;

	private Integer lower;

	private Integer upper;
	
	private ComponentType componentType;
	
	private String label;
	
	private boolean jsonIgnore;
	

	public FMProperty() {

	}

	public FMProperty(String name, MethodPropertyAccessModifier modifier) {
		super(name);
		this.accessModifier = modifier;
	}

	public FMProperty(FMType type, MethodPropertyAccessModifier accessModifier, Integer lower, Integer upper) {
		super();
		this.type = type;
		this.accessModifier = accessModifier;
		this.lower = lower;
		this.upper = upper;
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

	public ComponentType getComponentType() {
		return componentType;
	}

	public void setComponentType(ComponentType componentType) {
		this.componentType = componentType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean getJsonIgnore() {
		return jsonIgnore;
	}

	public void setJsonIgnore(boolean jsonIgnore) {
		this.jsonIgnore = jsonIgnore;
	}



}
