package main.java.data.parsing.entity;

public class Node {
	public Extension Extension;
	public String type;
	public String id;
	public String name;
	public String incoming;
	public String outgoing;
	public String inPartition;

	@Override
	public String toString() {
		return "Node{" +
				"Extension=" + Extension +
				", type='" + type + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", incoming='" + incoming + '\'' +
				", outgoing='" + outgoing + '\'' +
				", inPartition='" + inPartition + '\'' +
				'}';
	}
}
