package main.java.data.parsing.entity;

public class PackageImport {
	public Extension Extension;
	public ImportedPackage importedPackage;
	public String id;
	public String importingNamespace;

	@Override
	public String toString() {
		return "PackageImport{" +
				"Extension=" + Extension +
				", importedPackage=" + importedPackage +
				", id='" + id + '\'' +
				", importingNamespace='" + importingNamespace + '\'' +
				'}';
	}
}
