package com.promantus.Assessment.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.promantus.Assessment.Dto.GeneralQuestionDto;
import com.promantus.Assessment.Dto.TechQuestionDto;
import com.promantus.Assessment.Entity.TechQuestion;


public interface TechQuestionService {

	TechQuestionDto addTechQuestion(TechQuestionDto techQuestionDto, String lang) throws Exception;

	List<TechQuestionDto> getAllTechQuestions()throws Exception;

	TechQuestionDto getTechQuestionById(String id) throws Exception;

	TechQuestionDto updateTechQuestion(TechQuestionDto techQuestionDto, String lang) throws Exception;

	TechQuestionDto deleteTechQuestionById(String id) throws Exception;

	List<TechQuestionDto> findAndReplceByOtherTeamId(long findId, long replaceId) throws Exception;

	List<TechQuestionDto> searchtechQns(String type, String keyword) throws Exception;

	Map<String, Object> getAllTechQuestionsPage(Pageable paging) throws Exception;

	List<TechQuestionDto> activateAllTechQns() throws Exception;

	Map<String, Object> searchtechQnsPage(Pageable paging, String type, String keyword)throws Exception;

	TechQuestionDto inactiveTechQuestionById(String id) throws Exception;

	Map<String, Object> saveBulkTechQuestions(List<TechQuestion> technicalQuestion) throws Exception;
	

}
