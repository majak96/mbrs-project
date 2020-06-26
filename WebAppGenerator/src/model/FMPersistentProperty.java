package model;

public class FMPersistentProperty extends FMProperty {

	private Boolean id;
	private String columnName;
	private Integer length;
	private Integer scale;
	private Integer precision;
	private GeneratedValue generatedValue;
	private Boolean unique;

	public FMPersistentProperty() {
		super();
		this.id = false;
	}

	public FMPersistentProperty(String name, MethodPropertyAccessModifier modifier) {
		super(name, modifier);
		this.id = false;
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

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

}
