/**
 * 
 */
package com.promantus.Assessment.Dto;

import java.io.Serializable;

/**
 * @author Promantus
 *
 */
public class SettingsDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

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
	private int totalBeginnerMarks;
	private int totalIntermediateMarks;
	private int totalAdvancedMarks;
	private String isActive;
	private int status;
	private String message;
	
	
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getTotalBeginnerMarks() {
		return totalBeginnerMarks;
	}
	public void setTotalBeginnerMarks(int totalBeginnerMarks) {
		this.totalBeginnerMarks = totalBeginnerMarks;
	}
	public int getTotalIntermediateMarks() {
		return totalIntermediateMarks;
	}
	public void setTotalIntermediateMarks(int totalIntermediateMarks) {
		this.totalIntermediateMarks = totalIntermediateMarks;
	}
	public int getTotalAdvancedMarks() {
		return totalAdvancedMarks;
	}
	public void setTotalAdvancedMarks(int totalAdvancedMarks) {
		this.totalAdvancedMarks = totalAdvancedMarks;
	}
	
	
	
}
