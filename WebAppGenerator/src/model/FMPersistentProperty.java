package model;

public class FMPersistentProperty extends FMProperty {

	private Boolean id;
	private String columnName;
	private Integer length;
	private Integer precision;
	private GeneratedValue generatedValue;
	
	public FMPersistentProperty() {
		super();
	}
	
	public FMPersistentProperty(String name, MethodPropertyAccessModifier modifier) {
		super(name, modifier);
	}


	public FMPersistentProperty(FMType type, MethodPropertyAccessModifier accessModifier, Integer lower, Integer upper,
			Boolean unique, Boolean id, String columnName, Integer length, Integer precision,
			GeneratedValue generatedValue) {
		super(type, accessModifier, lower, upper, unique);
		this.id = id;
		this.columnName = columnName;
		this.length = length;
		this.precision = precision;
		this.generatedValue = generatedValue;
	}

	public Boolean getId() {
		return id;
	}
	public void setId(Boolean id) {
		this.id = id;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getPrecision() {
		return precision;
	}
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	public GeneratedValue getGeneratedValue() {
		return generatedValue;
	}
	public void setGeneratedValue(GeneratedValue generatedValue) {
		this.generatedValue = generatedValue;
	}
	
}
