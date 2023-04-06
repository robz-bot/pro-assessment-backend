package com.promantus.Assessment.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teams")
public class Settings {
	
	@Id
	private Long id;
	private int genQns;
	private int techQns;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getGenQns() {
		return genQns;
	}
	public void setGenQns(int genQns) {
		this.genQns = genQns;
	}
	public int getTechQns() {
		return techQns;
	}
	public void setTechQns(int techQns) {
		this.techQns = techQns;
	}
	
	

}
