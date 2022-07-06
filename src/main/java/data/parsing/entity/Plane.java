package main.java.data.parsing.entity;

import java.util.List;

public class Plane {
	public Extension Extension;
	public List<OwnedDiagramElements> ownedDiagramElements;
	public String id;
	public String modelElement;

	@Override
	public String toString() {
		return "Plane{" +
				"Extension=" + Extension +
				", ownedDiagramElements=" + ownedDiagramElements +
				", id='" + id + '\'' +
				", modelElement='" + modelElement + '\'' +
				'}';
	}
}
