package com.promantus.Assessment.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "settings")
public class Settings {
	
	@Id
	private Long id;
	private int genQns;
	private int techQns;
	private int beginner;
	private int intermediate;
	private int advanced;
	private int passPercentage;
	private int failPercentage;
	private int progPassPercentage;
	private int progFailPercentage;
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
	public int getBeginner() {
		return beginner;
	}
	public void setBeginner(int beginner) {
		this.beginner = beginner;
	}
	public int getIntermediate() {
		return intermediate;
	}
	public void setIntermediate(int intermediate) {
		this.intermediate = intermediate;
	}
	public int getAdvanced() {
		return advanced;
	}
	public void setAdvanced(int advanced) {
		this.advanced = advanced;
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
	public int getProgPassPercentage() {
		return progPassPercentage;
	}
	public void setProgPassPercentage(int progPassPercentage) {
		this.progPassPercentage = progPassPercentage;
	}
	public int getProgFailPercentage() {
		return progFailPercentage;
	}
	public void setProgFailPercentage(int progFailPercentage) {
		this.progFailPercentage = progFailPercentage;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
}
