package main.java.data.parsing.entity;

import java.util.List;

public class EAnnotations {
	public List<Details> details;
	public List<Contents> contents;
	public String id;
	public String source;

	@Override
	public String toString() {
		return "EAnnotations{" +
				"details=" + details +
				", contents=" + contents +
				", id='" + id + '\'' +
				", source='" + source + '\'' +
				'}';
	}
}
