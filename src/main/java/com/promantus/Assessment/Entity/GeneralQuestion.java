package com.promantus.Assessment.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "generalQuestion")
public class GeneralQuestion {

	@Id
	private Long id;
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String answer;
	private String updatedBy;
	private LocalDateTime updatedon;
	private boolean isActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(LocalDateTime updatedon) {
		this.updatedon = updatedon;
	}

	public boolean getisActive() {
		return isActive;
	}

	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
