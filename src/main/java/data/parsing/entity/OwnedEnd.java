package main.java.data.parsing.entity;

public class OwnedEnd {
	public Extension Extension;
	public LowerValue lowerValue;
	public UpperValue upperValue;
	public String id;
	public String name;
	public String type;
	public String owningAssociation;
	public String association;

	@Override
	public String toString() {
		return "OwnedEnd{" +
				"Extension=" + Extension +
				", lowerValue=" + lowerValue +
				", upperValue=" + upperValue +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				", owningAssociation='" + owningAssociation + '\'' +
				", association='" + association + '\'' +
				'}';
	}
}
