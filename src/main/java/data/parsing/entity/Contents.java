package main.java.data.parsing.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contents {
	//contents
	public Extension extension;

	@JsonProperty("plane")
	public Plane plane;
	public String type;
	public String id;
	public String name;
	public int width;
	public int height;

	@Override
	public String toString() {
		return "Contents{" +
				"extension=" + extension +
				", plane=" + plane +
				", type='" + type + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", width=" + width +
				", height=" + height +
				'}';
	}
}
