package main.java.data.parsing.entity;

public class Include {
	public Extension Extension;
	public String id;
	public String addition;
	public String includingCase;

	@Override
	public String toString() {
		return "Include{" +
				"Extension=" + Extension +
				", id='" + id + '\'' +
				", addition='" + addition + '\'' +
				", includingCase='" + includingCase + '\'' +
				'}';
	}
}
