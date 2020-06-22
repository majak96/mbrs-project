package model;

public class FMAssociation extends FMNamedElement {

	private FMLinkedProperty firstMemberEnd;
	private FMLinkedProperty secondMemberEnd;

	public FMAssociation(String name) {
		super(name);
	}

	public FMLinkedProperty getFirstMemberEnd() {
		return firstMemberEnd;
	}

	public void setFirstMemberEnd(FMLinkedProperty firstMemberEnd) {
		this.firstMemberEnd = firstMemberEnd;
	}

	public FMLinkedProperty getSecondMemberEnd() {
		return secondMemberEnd;
	}

	public void setSecondMemberEnd(FMLinkedProperty secondMemberEnd) {
		this.secondMemberEnd = secondMemberEnd;
	}

}
