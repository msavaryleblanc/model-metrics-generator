package main.java.data.parsing.entity;

public class UpperValue {
	public Extension Extension;
	public String type;
	public String id;
	public int value;

	@Override
	public String toString() {
		return "UpperValue{" +
				"Extension=" + Extension +
				", type='" + type + '\'' +
				", id='" + id + '\'' +
				", value=" + value +
				'}';
	}
}
