package model;

public class FMLinkedProperty extends FMProperty {

	private CascadeType cascade;
	private FetchType fetch;
	private String mappedBy;
	private Boolean orphanRemoval;
	private Boolean optional;
	private Boolean navigable;
	
	private FMAssociation association;
	
	public FMLinkedProperty() {
		super();
		this.navigable = true;
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

	public Boolean getNavigable() {
		return navigable;
	}

	public void setNavigable(Boolean navigable) {
		this.navigable = navigable;
	}

	public FMAssociation getAssociation() {
		return association;
	}

	public void setAssociation(FMAssociation association) {
		this.association = association;
	}

}
