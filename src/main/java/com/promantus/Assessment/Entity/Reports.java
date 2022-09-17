package com.promantus.Assessment.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
public class Reports {

	@Id
	private Long id;
	private Long userId;
	private String firstName;
	private String lastName;
	private String empCode;
	private String email;
	private String manager;
	private String teamId;
	private String percentage;
	private String status;
	private String totalNoOfQuestions;
	private String noOfQuestionsAnswered;
	private String noOfQuestionsNotAnswered;
	private String totalMarks;
	private LocalDateTime reportedOn;
	private LocalDateTime updatedOn;
	private String updatedBy;
	private boolean isActive;
	private int attempts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalNoOfQuestions() {
		return totalNoOfQuestions;
	}

	public void setTotalNoOfQuestions(String totalNoOfQuestions) {
		this.totalNoOfQuestions = totalNoOfQuestions;
	}

	public String getNoOfQuestionsAnswered() {
		return noOfQuestionsAnswered;
	}

	public void setNoOfQuestionsAnswered(String noOfQuestionsAnswered) {
		this.noOfQuestionsAnswered = noOfQuestionsAnswered;
	}

	public String getNoOfQuestionsNotAnswered() {
		return noOfQuestionsNotAnswered;
	}

	public void setNoOfQuestionsNotAnswered(String noOfQuestionsNotAnswered) {
		this.noOfQuestionsNotAnswered = noOfQuestionsNotAnswered;
	}

	public String getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(String totalMarks) {
		this.totalMarks = totalMarks;
	}

	public LocalDateTime getReportedOn() {
		return reportedOn;
	}

	public void setReportedOn(LocalDateTime reportedOn) {
		this.reportedOn = reportedOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	
}
