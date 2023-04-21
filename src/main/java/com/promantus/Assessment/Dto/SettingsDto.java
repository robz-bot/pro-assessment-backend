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
	private int passPercentage;
	private int failPercentage;
	private String isActive;
	private int status;
	private String message;
	
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
  	

}
