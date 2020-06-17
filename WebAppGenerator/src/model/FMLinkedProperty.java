package model;

public class FMLinkedProperty extends FMProperty {

	private CascadeType cascade;
	private FetchType fetch;
	private String mappedBy;
	private Boolean orphanRemoval;
	private Boolean optional;

	public FMLinkedProperty() {
		super();
	}

	public FMLinkedProperty(FMType type, MethodPropertyAccessModifier accessModifier, Integer lower, Integer upper,
			Boolean unique, CascadeType cascade, FetchType fetch, String mappedBy, Boolean orphanRemoval,
			Boolean optional) {
		super(type, accessModifier, lower, upper, unique);
		this.cascade = cascade;
		this.fetch = fetch;
		this.mappedBy = mappedBy;
		this.orphanRemoval = orphanRemoval;
		this.optional = optional;
	}

	public CascadeType getCascade() {
		return cascade;
	}

	public void setCascade(CascadeType cascade) {
		this.cascade = cascade;
	}

	public FetchType getFetch() {
		return fetch;
	}

	public void setFetch(FetchType fetch) {
		this.fetch = fetch;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public Boolean getOrphanRemoval() {
		return orphanRemoval;
	}

	public void setOrphanRemoval(Boolean orphanRemoval) {
		this.orphanRemoval = orphanRemoval;
	}

	public Boolean getOptional() {
		return optional;
	}

	public void setOptional(Boolean optional) {
		this.optional = optional;
	}

}
