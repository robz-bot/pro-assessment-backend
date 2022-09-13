package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.GeneralQuestionDto;


public interface GeneralQuestionService {

	GeneralQuestionDto addGeneralQuestion(GeneralQuestionDto generalQuestionDto, String lang) throws Exception;

	List<GeneralQuestionDto> getAllGeneralQuestions()throws Exception;

	GeneralQuestionDto updateGeneralQuestion(GeneralQuestionDto generalQuestionDto, String lang) throws Exception;

	GeneralQuestionDto deleteGeneralQuestionById(String id) throws Exception;

	GeneralQuestionDto getGeneralQuestionById(String id) throws Exception;      

	List<GeneralQuestionDto> search(String type, String keyword) throws Exception;

	

}
