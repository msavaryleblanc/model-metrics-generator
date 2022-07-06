package main.java.data.parsing.entity;

public class Edge {
	public Extension Extension;
	public Guard guard;
	public String type;
	public String id;
	public String name;
	public String activity;
	public String target;
	public String source;

	@Override
	public String toString() {
		return "Edge{" +
				"Extension=" + Extension +
				", guard=" + guard +
				", type='" + type + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", activity='" + activity + '\'' +
				", target='" + target + '\'' +
				", source='" + source + '\'' +
				'}';
	}
}
