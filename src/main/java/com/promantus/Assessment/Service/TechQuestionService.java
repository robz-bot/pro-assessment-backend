package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.TechQuestionDto;


public interface TechQuestionService {

	TechQuestionDto addTechQuestion(TechQuestionDto techQuestionDto, String lang) throws Exception;

	List<TechQuestionDto> getAllTechQuestions()throws Exception;

	TechQuestionDto getTechQuestionById(String id) throws Exception;

	TechQuestionDto updateTechQuestion(TechQuestionDto techQuestionDto, String lang) throws Exception;

	TechQuestionDto deleteTechQuestionById(String id) throws Exception;

}
