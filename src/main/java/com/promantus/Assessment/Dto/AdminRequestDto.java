package com.promantus.Assessment.Dto;

import java.time.LocalDateTime;

public class AdminRequestDto {

	private Long id;
	private String email;
	private Long teamId;
	private String team;
	private String reason;
	private String password;
	private LocalDateTime reqRaisedOn;
	private LocalDateTime reqApproveOrDeclineOn;
	private boolean isApprove;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public LocalDateTime getReqRaisedOn() {
		return reqRaisedOn;
	}
	public void setReqRaisedOn(LocalDateTime reqRaisedOn) {
		this.reqRaisedOn = reqRaisedOn;
	}
	public LocalDateTime getReqApproveOrDeclineOn() {
		return reqApproveOrDeclineOn;
	}
	public void setReqApproveOrDeclineOn(LocalDateTime reqApproveOrDeclineOn) {
		this.reqApproveOrDeclineOn = reqApproveOrDeclineOn;
	}
	public boolean isApprove() {
		return isApprove;
	}
	public void setApprove(boolean isApprove) {
		this.isApprove = isApprove;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
