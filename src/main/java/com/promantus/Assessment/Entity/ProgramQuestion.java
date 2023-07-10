package com.promantus.Assessment.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "programQuestion")
public class ProgramQuestion {
	
		@Id
		private Long id;
		private String teamId;
		private String program;
		private String programLevel;
	    private String createdBy;
		private LocalDateTime createdOn;
		private String updatedBy;
	    private LocalDateTime updatedOn;
		private String date;
		private boolean isActive;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTeamId() {
			return teamId;
		}
		public void setTeamId(String teamId) {
			this.teamId = teamId;
		}
		public String getProgram() {
			return program;
		}
		public void setProgram(String program) {
			this.program = program;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public LocalDateTime getCreatedOn() {
			return createdOn;
		}
		public void setCreatedOn(LocalDateTime createdOn) {
			this.createdOn = createdOn;
		}
		public String getUpdatedBy() {
			return updatedBy;
		}
		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}
		public LocalDateTime getUpdatedOn() {
			return updatedOn;
		}
		public void setUpdatedOn(LocalDateTime updatedOn) {
			this.updatedOn = updatedOn;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public boolean isActive() {
			return isActive;
		}
		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
		public String getProgramLevel() {
			return programLevel;
		}
		public void setProgramLevel(String programLevel) {
			this.programLevel = programLevel;
		}
		
    
				
}
