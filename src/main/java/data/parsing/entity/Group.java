package main.java.data.parsing.entity;

public class Group {
	public Extension Extension;
	public String type;
	public String id;
	public String name;
	public String node;

	@Override
	public String toString() {
		return "Group{" +
				"Extension=" + Extension +
				", type='" + type + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", node='" + node + '\'' +
				'}';
	}
}
