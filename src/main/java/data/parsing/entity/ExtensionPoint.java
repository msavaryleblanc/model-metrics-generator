package main.java.data.parsing.entity;

public class ExtensionPoint {
	public Extension Extension;
	public String id;
	public String name;
	public String useCase;

	@Override
	public String toString() {
		return "ExtensionPoint{" +
				"Extension=" + Extension +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", useCase='" + useCase + '\'' +
				'}';
	}
}
