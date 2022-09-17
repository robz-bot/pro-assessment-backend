package com.promantus.Assessment.Dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TeamDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String team;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private String updatedBy;
	private boolean isActive;
	private int status;
	private String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean getisActive() {
		return isActive;
	}

	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
