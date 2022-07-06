package main.java.data.parsing.entity;

public class Extend {
	public Extension Extension;
	public String id;
	public String extendedCase;
	public String extensionLocation;
	public String extension;

	@Override
	public String toString() {
		return "Extend{" +
				"Extension=" + Extension +
				", id='" + id + '\'' +
				", extendedCase='" + extendedCase + '\'' +
				", extensionLocation='" + extensionLocation + '\'' +
				", extension='" + extension + '\'' +
				'}';
	}
}
