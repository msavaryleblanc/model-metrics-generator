package main.java.data.parsing.entity;

public class Guard {
	public Extension Extension;
	public String type;
	public String id;
	public boolean value;

	@Override
	public String toString() {
		return "Guard{" +
				"Extension=" + Extension +
				", type='" + type + '\'' +
				", id='" + id + '\'' +
				", value=" + value +
				'}';
	}
}
