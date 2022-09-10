package com.promantus.Assessment.Dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReportsDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private String teamId;
	private String percentage;
	private String totalNoOfQuestions;
	private String noOfQuestionsAnswered;
	private String noOfQuestionsNotAnswered;
	private String totalMarks;
	private String status;
	private LocalDateTime reportedOn;
	private LocalDateTime updatedOn;
	private String message;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setTeamId(String teamId) {
		// TODO Auto-generated method stub
		
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
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
	public String getTeamId() {
		return teamId;
	}
	
	
}
