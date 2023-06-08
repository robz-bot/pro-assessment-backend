package com.promantus.Assessment.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "settings")
public class Settings {
	
	@Id
	private Long id;
	private int genQns;
	private int techQns;
	private int progQns;
	private int passPercentage;
	private int failPercentage;
	private String isActive;
	
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
	public int getPassPercentage() {
		return passPercentage;
	}
	public void setPassPercentage(int passPercentage) {
		this.passPercentage = passPercentage;
	}
	public int getFailPercentage() {
		return failPercentage;
	}
	public void setFailPercentage(int failPercentage) {
		this.failPercentage = failPercentage;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public int getProgQns() {
		return progQns;
	}
	public void setProgQns(int progQns) {
		this.progQns = progQns;
	}
	
	
 
}
