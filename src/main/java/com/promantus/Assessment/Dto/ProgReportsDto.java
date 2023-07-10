/**
 * 
 */
package com.promantus.Assessment.Dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Vishalraj
 *
 */

public class ProgReportsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private Long userId;
	private String teamId;
	private String percentage;
	private String Status;
	
	private String teamName;
	private String userName;

	private String question;
	private String answer;
	private String remarks;
	private String level; // B - Beginner, I - Intermediate, A - Advanced

	private String totalMark;
	private String scoredMark;

	private String reportedOn;
	private LocalDateTime updatedOn;
	private String updatedBy;
	private boolean isActive;
	private int attempts;
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
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(String totalMark) {
		this.totalMark = totalMark;
	}

	public String getScoredMark() {
		return scoredMark;
	}

	public void setScoredMark(String scoredMark) {
		this.scoredMark = scoredMark;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReportedOn() {
		return reportedOn;
	}

	public void setReportedOn(String reportedOn) {
		this.reportedOn = reportedOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

}
