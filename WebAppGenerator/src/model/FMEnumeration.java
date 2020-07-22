package model;

import java.util.List;

public class FMEnumeration extends FMType {
	
	private List<String> options;

	public FMEnumeration(String name, String typePackage) {
		super(name, typePackage);
		// TODO Auto-generated constructor stub
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}


}
